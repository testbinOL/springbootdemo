package com.bin.demo.utils;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import java.nio.charset.Charset;

/**
 * Author: xingshulin Date: 2019/4/15 下午10:31
 *
 *
 * Description: zookeeper自定义序列化类 Version: 1.0
 **/
public class MyZkSerializer implements ZkSerializer {

  String charset = "UTF-8";

  @Override
  public byte[] serialize(Object o) throws ZkMarshallingError {
    return String.valueOf(o).getBytes(Charset.forName(charset));
  }

  @Override
  public Object deserialize(byte[] bytes) throws ZkMarshallingError {
    return new String(bytes, Charset.forName(charset));
  }
}
