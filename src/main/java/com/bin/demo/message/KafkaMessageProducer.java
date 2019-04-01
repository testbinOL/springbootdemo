package com.bin.demo.message;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

/**
 * Author: xingshulin Date: 2019/3/27 上午11:41
 *
 *
 * Description: kafka消息生产者
 **/
public class KafkaMessageProducer {

  public static void main(String[] args) {
    KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer();
    kafkaMessageProducer.sendMessage();
  }

  public void sendMessage() {
    Properties properties = new Properties();
    properties.put("bootstrap.servers", "127.0.0.1:9092");
    properties.put("partitioner.class", "com.bin.demo.message.CustomerPartitioner");
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    String topic = "test";
    try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
      System.out.println("分片：{}" + kafkaProducer.partitionsFor(topic));

      int i = 0;
      boolean loop = true;
      while (loop) {
        String msg = "test" + ++i;
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, msg);
        System.out.println("---消息:" + msg);
        kafkaProducer.send(producerRecord);
        Thread.sleep(5000);
      }
    } catch (InterruptedException e) {
      System.out.println("线程中断异常" + e.getMessage());
    }
  }


}
