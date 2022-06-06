package com.block_pipe.utils;

import com.block_pipe.model.Cls;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class WriteOrc {
  public static Writer writer = null;
  static String ClusterName = "ns00";

  private static final String HADOOP_URL = "hdfs://" + ClusterName;

  public static Configuration conf;

  static {

    conf = new Configuration();

    conf.set("fs.defaultFS", HADOOP_URL);

    conf.set("dfs.nameservices", ClusterName);

    conf.set("dfs.ha.namenodes." + ClusterName, "nn1,nn2");

    conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn1", "hpc02:9000");

    conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn2", "hpc03:9000");

    // conf.setBoolean(name, value);

    conf.set("dfs.client.failover.proxy.provider." + ClusterName,

        "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

  }

  public static void action(ConsumerRecords<String, byte[]> records,String yyyy,String mm,String dd) throws java.io.IOException {
    TypeDescription schema = TypeDescription.createStruct()
        .addField("timestamp", TypeDescription.createLong())
        .addField("data", TypeDescription.createBinary());

    FileSystem fs=null;
    String fileName = String.format("/sxx/archive/%s/%s/%s_COPYING.orc", yyyy,mm,dd);
    String fileFinished = String.format("/sxx/archive/%s/%s/%s.orc", yyyy,mm,dd);
    Path path = new Path(fileName);

    try {
      fs = path.getFileSystem(conf);
      if (fs.exists(path)) {
        // fs.delete(path, true);
        System.out.println(fileName+"existed");
      }

    } catch (Exception e) {

      e.printStackTrace();

    }
    if (writer == null) {
      System.out.println("create");
      writer = OrcFile.createWriter(path, OrcFile.writerOptions(conf).setSchema(schema).stripeSize(67108864)
          .bufferSize(131072).blockSize(134217728).compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_12));
    }
    VectorizedRowBatch batch = schema.createRowBatch();
    LongColumnVector longVector = (LongColumnVector) batch.cols[0];
    BytesColumnVector stringVector = (BytesColumnVector) batch.cols[1];
    int row = batch.size;
    for (ConsumerRecord<String, byte[]> record : records) {
      row = batch.size++;
      Long timestamp = Cls.StreamField.parseFrom(record.value()).getTimeStamp();
      System.out.println("timestamp:" + timestamp + "" + ",value:" + record.value() + ",row" + row);
      longVector.vector[row] = timestamp;
      stringVector.setVal(row, record.value());
      if (batch.size == batch.getMaxSize()) {
        writer.addRowBatch(batch);
        batch.reset();
      }

    }

    if (batch.size != 0) {
      writer.addRowBatch(batch);
      batch.reset();
    }
    if (records.isEmpty()) {
      System.out.println("close");
      writer.close();
      Path oldPath =path;
      Path newPath =new Path(fileFinished);
      fs.rename(oldPath,newPath);
      writer = null;
    }
  }

}