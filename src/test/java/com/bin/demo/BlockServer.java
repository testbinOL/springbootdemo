package com.bin.demo;

import org.apache.commons.lang3.StringUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: xingshulin Date: 2019/4/8 下午3:00
 *
 *
 * Description: TODO Version: 1.0
 **/
public class BlockServer {

  public static void main(String[] args) {
    //blockServer();
    try (ServerSocketChannel channel = ServerSocketChannel.open()) {
      Selector selector = Selector.open();
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      selectionKeys.stream().forEach(selectionKey -> {
        if (selectionKey.isReadable()) {
          SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          try {
            socketChannel.read(buffer);
            
          } catch (IOException e) {
            e.printStackTrace();
          }
          System.out.println();
        }
      });
      channel.register(selector, SelectionKey.OP_CONNECT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void blockServer() {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    int port = 8080;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("监听端口:" + port);
      //serverSocket.setSoTimeout(100000);
      while (true) {
        System.out.println("进入");
        Socket socket = serverSocket.accept();
        System.out.println(Thread.currentThread().getName() + ":接收连接");
        executorService.submit(() -> {
          try (InputStream inputStream = socket.getInputStream()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            byte[] temp = new byte[1024];
            int read = bufferedInputStream.read(temp);
            System.out.println(Thread.currentThread().getName() + " :处理接收到的连接");
            int total = 0;
            while (read != -1) {
              System.out.println("接收到的可用字节数：" + read);
              stringBuilder.append(new String(temp));
              read = bufferedInputStream.read(temp, total, temp.length - total);
              total += read;
            }
            System.out.println("received message : " + StringUtils.trim(stringBuilder.toString()));
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        });
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
