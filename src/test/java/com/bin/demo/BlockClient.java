package com.bin.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author: xingshulin Date: 2019/4/8 下午2:41
 *
 *
 * Description: TODO Version: 1.0
 **/
public class BlockClient {

  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1024);
    ExecutorService executorService = Executors.newFixedThreadPool(1024);
    for (int i = 0; i < 1024; i++) {
      executorService.submit(() -> {
        Socket client = new Socket();
        try {
          client.setSoTimeout(5000);
          client.connect(new InetSocketAddress("localhost", 8080), 10000);
          OutputStream outputStream = client.getOutputStream();
          BufferedWriter writer = new BufferedWriter(
              new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
          String threadName = Thread.currentThread().getName();
          writer.write(threadName);
          System.out.println("线程" + threadName + "发送消息");
          writer.flush();
          writer.close();
          countDownLatch.countDown();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
    countDownLatch.await();
    System.out.println("所有客户端执行完成");
    executorService.shutdown();
    executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);


  }

}
