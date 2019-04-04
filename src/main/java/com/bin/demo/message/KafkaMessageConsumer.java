package com.bin.demo.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * Author: xingshulin Date: 2019/3/27 下午2:39
 *
 *
 * Description: kafka消费者
 **/
public class KafkaMessageConsumer {


  public static void main(String[] args) {
    KafkaMessageConsumer kafkaMessageConsumer = new KafkaMessageConsumer();
    kafkaMessageConsumer.receiveStreamMessage();
  }

  private void receiveStreamMessage() {

    Properties properties = new Properties();
    properties.put("zookeeper.connect", "localhost:2181");
    properties.put("group.id", "cg.nick");
    properties.put("consumer.id", "c.nick");
    ConsumerConfig consumerConfig = new ConsumerConfig(properties);
    consumerConfig.autoOffsetReset();
    String topic = "test";

    ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

    Map<String, Integer> topicCountMap = new HashMap<>();
    topicCountMap.put(topic, 3);
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
        .createMessageStreams(topicCountMap);

    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
    ExecutorService executor = Executors.newFixedThreadPool(3);
    System.out.println("获取到的流数量：" + streams.size());
    for (final KafkaStream stream : streams) {
      executor.submit(() -> {
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
          MessageAndMetadata<byte[], byte[]> mm = it.next();
          System.out.println(String
              .format("thread: %s ,partition = %s, offset = %d, key = %s, value = %s",
                  Thread.currentThread().getName(), mm.partition(),
                  mm.offset(), mm.key(), new String(mm.message())));
        }
      });
    }
  }

  private void pullMessage() {
    Properties properties = new Properties();
    properties.put("bootstrap.servers", "localhost:9092");
    properties.put("group.id", "cg.nick");
    properties.put("consumer.id", "c.nick");
    //properties.put("auto.offset.reset", "earliest");
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    properties
        .put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
        properties)) {
      String topic = "test";
      boolean loop = true;
      //consumer.subscribe(Collections.singletonList(topic));
      List<TopicPartition> topicPartitions = consumer.partitionsFor(topic).stream()
          .map(partitionInfo -> new TopicPartition(topic, partitionInfo.partition())).collect(
              Collectors.toList());
      consumer.assign(topicPartitions);
      consumer.seekToBeginning(topicPartitions);
      while (loop) {
        ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(100));
        for (ConsumerRecord consumerRecord : consumerRecords) {
          System.out.println("key:" +
              consumerRecord.key() + ":value:" + consumerRecord.value() + ":offset:"
              + consumerRecord.offset()
              + ":part:" + consumerRecord.partition());
        }
        Thread.sleep(5000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
