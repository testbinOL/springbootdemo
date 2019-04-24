package com.bin.demo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Author: xingshulin Date: 2019/4/4 下午2:29
 *
 *
 * Description: Version: 1.0
 **/
public class Io {

  public static void main(String[] args) {
    //BubbleSort();
    //fileChannel();
  }


  private static void fileChannel() {
    try {
      URL fileUrl = ClassLoader.getSystemResource("static/content.txt");
      File file;
      if (null == fileUrl || !(file = new File(fileUrl.toURI())).exists()) {
        System.out.println("file is not exist!!!");
        return;
      }
      RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
      FileChannel fileChannel = randomAccessFile.getChannel();
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
      int read = fileChannel.read(byteBuffer);
      StringBuilder stringBuilder = new StringBuilder();
      while (read != -1) {
        byte[] temp = new byte[read];
        System.out.println("received byte num:" + read);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
          byteBuffer.get(temp, 0, read);
          stringBuilder.append(new String(temp));
          System.out.println("received content:" + stringBuilder.toString());
        }
        byteBuffer.clear();
        read = fileChannel.read(byteBuffer);
      }
      randomAccessFile.close();
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  private static void BubbleSort() {
    int[] arrs = {1, 2, 3, 4, 7, 33, 12, 9, 32};
    int temp;
    for (int i = 0; i < arrs.length; i++) {
      for (int j = 0; j < arrs.length - i - 1; j++) {
        if (arrs[j] < arrs[j + 1]) {
          temp = arrs[j];
          arrs[j] = arrs[j + 1];
          arrs[j + 1] = temp;
        }
      }
    }
    System.out.println(Arrays.toString(arrs));
  }
}
