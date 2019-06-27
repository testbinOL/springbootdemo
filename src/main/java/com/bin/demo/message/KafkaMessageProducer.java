package com.bin.demo.message;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        Future<RecordMetadata> future = kafkaProducer.send(producerRecord, new Callback() {
          @Override
          public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            System.out.println("响应结果返回：" + recordMetadata.offset());
          }
        });
        Thread.sleep(5000);
        System.out.println("同步返回结果：" + future.get().offset());
      }
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("线程中断异常" + e.getMessage());
    }
  }


}
