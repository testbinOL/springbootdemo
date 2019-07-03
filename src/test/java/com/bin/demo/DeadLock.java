package com.bin.demo;

/**
 * Author: xingshulin Date：2019/6/28 下午6:23 Description: 死锁实例
 **/
public class DeadLock {

  private static final String lock_one = "a";

  private static final String lock_two = "b";

  private static volatile boolean b = true;

  public static void main(String[] args) {

    Thread thread = new Thread(() -> {

      synchronized (lock_one) {
        try {
          System.out.println(Thread.currentThread().getName() + ":进入锁A");
          Thread.sleep(5000);
          System.out.println(Thread.currentThread().getName() + "退出睡眠状态");
        } catch (Exception e) {
          e.printStackTrace();
        }
        synchronized (lock_two) {
          System.out.println(Thread.currentThread().getName() + ":获取锁");
        }
      }
    });

    Thread thread1 = new Thread(() -> {
      synchronized (lock_two) {
        System.out.println(Thread.currentThread().getName() + ":进入锁B");
        synchronized (lock_one) {
          System.out.println(Thread.currentThread().getName() + ":获取锁A");
        }
      }
    });

    thread.start();
    thread1.start();

    try {
      Thread.sleep(3000);
      System.out.println("等待结束");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
