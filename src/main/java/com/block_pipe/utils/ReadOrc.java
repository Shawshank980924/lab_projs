package com.block_pipe.utils;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.block_pipe.model.Cls;
import com.block_pipe.model.Cls.StreamField;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.io.sarg.SearchArgumentFactory;
import org.apache.log4j.Logger;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.orc.TypeDescription;

public class ReadOrc {
  //hdfs集群名
  static String ClusterName = "ns00";
  //hdfs集群地址
  private static final String HADOOP_URL = "hdfs://" + ClusterName;

  public static Configuration conf;
  //日志log4j
  public static Logger logger;
  //配置
  static {
    //hdfs配置，参考/opt/hadoop-2.7.7/etc/hadoop/hdfs-site.xml
    conf = new Configuration();
    //hdfs集群地址
    conf.set("fs.defaultFS", HADOOP_URL);
    //集群命名空间
    conf.set("dfs.nameservices", ClusterName);
    //namenode地址一主一备
    conf.set("dfs.ha.namenodes." + ClusterName, "nn1,nn2");
    //namenode rpc地址
    conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn1", "hpc02:9000");
    conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn2", "hpc03:9000");
    //客户端连接可用状态的NameNode所用的代理类
    conf.set("dfs.client.failover.proxy.provider." + ClusterName,
        "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
	//配置日志log4j
    logger = Logger.getLogger(ReadOrc.class);

  }
  //拉取hdfs对应日期的orc数据备份文件
  public static void read(String date) throws java.io.IOException{


      //date格式:"yyyy_mm_dd"
      //分割字符串年月日
      String[] strs = date.split("_");
      String yyyy=strs[0],mm=strs[1],dd=strs[2];
      FileSystem fs;
      //生成hdfs存储地址
      String fileName = String.format("/sxx/archive/%s/%s/%s.orc", yyyy,mm,dd);
      Path path = new Path(fileName);
      //判断该文件是否存在，若不存在抛出错误
      try {
        fs = path.getFileSystem(conf);
        if (!fs.exists(path)) {
          // fs.delete(path, true);
          throw new FileNotFoundException(String.format("%s not found",fileName));
        }
  
      } catch (Exception e) {
  
        e.printStackTrace();
  
      }

      //指定orc文件的schema，两列一列为事件时间的时间戳，一列是streamField的字节流
      TypeDescription readSchema = TypeDescription.createStruct()
        .addField("timestamp", TypeDescription.createLong())
        .addField("data", TypeDescription.createBinary());

      Reader reader = OrcFile.createReader(path,
      OrcFile.readerOptions(conf));
    //生成orcreader
    Reader.Options readerOptions = new Reader.Options(conf)
        .searchArgument(
            SearchArgumentFactory
                .newBuilder()
                .build(),
            new String[]{"timestamp","data"}
        );
    //指定读取文件的schema
    RecordReader rows = reader.rows(readerOptions.schema(readSchema));

    VectorizedRowBatch batch = readSchema.createRowBatch();
    
    // System.out.println(reader.getNumberOfRows()); 
    logger.info(date+" number of rows "+reader.getNumberOfRows());
	//按batch读取orc文件中的数据
    while (rows.nextBatch(batch)) {
		//指定数据落地的容器
      LongColumnVector longVector = (LongColumnVector) batch.cols[0];
      BytesColumnVector stringVector = (BytesColumnVector)  batch.cols[1];
		//遍历每一批数据
      for(int r=0; r < batch.size; r++) {
        long timestamp = longVector.vector[r];
        byte[] bytes=stringVector.vector[r];

        // byte[] bytes=new ReadOrc();
        // stringVector.fill(bytes);
        // logger.info(bytes);
		//反序列化
        StreamField sf = Cls.StreamField.parseFrom(bytes);
        // String stringValue = stringVector.toString(r);
		//写入日志
        logger.info(sf);
		//logger.info(logValue);
        // System.out.println(timestamp + ", " + bytes);


        }
    }
        rows.close();

  }
  public static void main(String[] args) throws IOException {
    // List<String> list = TimeUtil.getDaysByYear(2015,8,7);
    //     for(String day:list){
    //       read(day);

    //     }
    read("2016_08_07");
  }

}