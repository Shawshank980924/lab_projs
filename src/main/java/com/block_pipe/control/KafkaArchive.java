/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-05-13 12:43:08
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-06-06 11:58:21
 * @FilePath: /datapipe/src/main/java/com/datapipe/control/KafkaArchive.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.block_pipe.control;

import com.block_pipe.utils.WriteOrc;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaArchive {

    Properties props = new Properties();

    public KafkaArchive() {
        Random r = new Random();
        props.put("bootstrap.servers", "hpc01:9092");
        props.put("group.id", Integer.toString(r.nextInt()));
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", ByteArrayDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        props.put("auto.offset.reset", "earliest");
    }

    public void archive(String date) throws java.io.IOException {
        String[] strs = date.split("_");
        String yyyy = strs[0], mm = strs[1], dd = strs[2];
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);
        consumer.subscribe(Arrays.asList("trade_" + date));
        boolean flag = true;
        while (flag) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(1000));
            // 执行业务逻辑
            WriteOrc.action(records, yyyy, mm, dd);
            // 手动提交commit
            consumer.commitAsync();
            System.out.println(records.count());
            if (records.isEmpty()) {
                System.out.println("empty");
                flag = false;
            }
            // for(ConsumerRecord<String,byte[]> record : records){
            // Long timestamp=Cls.StreamField.parseFrom(record.value()).getTimestamp();
            // System.out.println("timestamp:" + timestamp + "" + ",value:" +
            // record.value());
            // }
        }

    }

    public static void main(String[] args) throws IOException {
        String date = "2021_11_17";
        new KafkaArchive().archive("2021_11_17");
    }

}
