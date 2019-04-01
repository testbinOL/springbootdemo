package com.bin.demo.message;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: xingshulin Date: 2019/3/27 下午5:09
 *
 *
 * Description: 自定义分区器
 **/
public class CustomerPartitioner implements Partitioner {

  @Override
  public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1,
      Cluster cluster) {
    String value = o1.toString();
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(value);
    Integer intValue = (int) Math.random() * 3;
    if (matcher.find()) {
      intValue = Integer.valueOf(value.substring(matcher.start(), matcher.end()));
      return Math.floorMod(intValue, 3);
    }
    return intValue;
  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> map) {

  }
}
