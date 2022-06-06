/*
 * @Author: your name
 * @Date: 2022-04-24 19:18:06
 * @LastEditTime: 2022-06-06 10:59:09
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /datapipe/src/main/java/com/datapipe/Kafka2nebula.java
 */
package com.block_pipe.control;

import com.block_pipe.utils.GqlGen;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.block_pipe.model.Cls;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.log4j.Logger;

// import com.datapipe.Cls;
public class Kafka2nebula {

    private static String INPUT_TOPIC;
    private static NebulaPool pool = new NebulaPool();
    private static Logger logger = Logger.getLogger(Kafka2nebula.class);

    /**
     * 构建配置属性
     */
    public static Properties getProperties() {
        Properties properties = new Properties();
        Random r = new Random();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "hpc01:9092");// 连接kafka客户端端口
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, Integer.toString(r.nextInt()));// 指定用户组，相同的用户内部互补消费
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());// 指定key 序列化器
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());// value暂时以字节流读入，后续处理
        properties.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);// 调整kafka stream的并发度等于分区数
        return properties;
    }

    public static boolean initNebulaPool() {
        try {
            NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
            nebulaPoolConfig.setMaxConnSize(100);
            List<HostAddress> addresses = Arrays.asList(new HostAddress("127.0.0.1", 9669));
            Boolean initResult = pool.init(addresses, nebulaPoolConfig);
            if (!initResult) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static KafkaStreams createKafkaStreams(String topicName) throws Exception {

        INPUT_TOPIC = topicName;
        Properties properties = getProperties();
        boolean initResult = initNebulaPool();
        if (initResult == false)
            return null;
        // 构建流结构拓扑
        StreamsBuilder builder = new StreamsBuilder();
        // 构建该stream的processor
        foreachStream(builder);
        Topology topology = builder.build();

        // 构建KafkaStreams
        return new KafkaStreams(topology, properties);
    }

    /*
     * 对于流中的每个元素写入nebula
     */
    public static void foreachStream(StreamsBuilder builder) throws Exception {

        KStream<String, byte[]> source = builder.stream(INPUT_TOPIC);
        Session session = pool.getSession("root", "nebula", false);
        int[] count = new int[1];
        source.foreach((k, v) -> {

            String insertGen;
            try {

                long trade_time = Cls.StreamField.parseFrom(v).getTimeStamp();
                insertGen = GqlGen.getGen(v);
                ResultSet resp = session.execute(insertGen);
                if (!resp.isSucceeded()) {

                    System.out.println(String.format("Execute: `%s', failed: %s",
                            insertGen, resp.getErrorMessage()));
                    System.exit(1);
                }
                // long now_time = System.currentTimeMillis() / 1000;
                // String str = String.valueOf(now_time) + "-" + String.valueOf(trade_time) +
                // "=";
                // System.out.println(str + String.valueOf(now_time - trade_time));
                // System.out.println(insertGen);
                logger.info(insertGen);

                // System.out.println("ok");

                // System.out.println("success "+count[0]);
                count[0] = count[0] + 1;
            } catch (Exception e) {
                System.out.println("gg");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            // SimpleDateFormat df = new SimpleDateFormat("edge_yyyy_MM_dd");// 设置日期格式
            // String date = df.format(new Date());

            // KafkaStreams streams1 = createKafkaStreams("account");
            KafkaStreams streams = createKafkaStreams("Archive_2017_04_28");
            // final CountDownLatch latch = new CountDownLatch(1);
            // 启动该Stream
            // streams1.start();
            streams.start();
            Thread.sleep(1000 * 3600 * 24);
            System.out.println(streams.close(Duration.ofMillis(10000)));

        }
    }
}