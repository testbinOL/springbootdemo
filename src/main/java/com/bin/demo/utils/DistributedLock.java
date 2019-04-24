package com.bin.demo.utils;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xingshulin
 * @Date: 2019/4/10 上午10:24
 * @Description: 分布式锁实现
 * @Version: 1.0
 **/
@Component
public class DistributedLock {

  public static void main(String[] args) throws IOException {
    //test();
  }

  private static void test() {
    Config config = new Config();
    RedissonClient redissonClient = Redisson.create(config);
    String value = null;
    final RLock lock = redissonClient.getLock("test_lock");
    try {
      /**
       * 编解码问题：使用JsonJacksonCodec类时，对redis中的字符串进行解码时，如果字符串形式为无引号状态会出现异常
       * 异常字符串形式：test
       * 可解码字符串形式：\"test\"
       *
       * 异常信息：com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'main': was expecting ('true', 'false' or 'null')
       *
       * 总结：对存入redis的值要保持存取的编解码一致
       */
      redissonClient.getBucket("test")
          .set(Thread.currentThread().getName(), 10000, TimeUnit.MILLISECONDS);
      System.out.println("设置test");
      Thread.sleep(5000);
      value = (String) redissonClient.getBucket("test")
          .get();

      /**
       * tryLock方法默认释放锁时间为30秒
       * redission 内部提供了一个监控锁的看门狗，在redission实例关闭前会不断延迟锁的到期时间，
       * 目的是防止由于线程在到期时间内未运行完导致的两个线程在临界区同时运行
       * 当实例关闭后，并且锁的释放时间到期 释放锁。
       * redisson 锁为可重入锁
       *
       * 注意：看门狗只有在未明确指明releasetime 的情况下使用。
       *
       */
      boolean isLock = lock.tryLock(5000, TimeUnit.MILLISECONDS);
      if (isLock) {
        System.out.println("加锁1成功");
      } else {
        System.out.println("加锁失败");
      }

      ExecutorService executorService = Executors.newSingleThreadExecutor();
      executorService.submit(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + "开始获取锁");
          boolean ifLocked = lock.tryLock(20000, TimeUnit.MILLISECONDS);
          if (ifLocked) {
            System.out.println(Thread.currentThread().getName() + "加锁成功");
            Thread.sleep(10000);
          } else {
            System.out.println(Thread.currentThread().getName() + "加锁失败");
          }

        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      });

      Thread.sleep(2000);

      boolean isLock1 = lock.tryLock(5000, TimeUnit.MILLISECONDS);
      if (isLock1) {
        System.out.println("加锁2成功");
        Thread.sleep(10000);
      } else {
        System.out.println("加锁2失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != lock) {
        lock.unlock();
      }
      System.out.println("锁释放一次完成");
      if (null != lock) {
        lock.unlock();
      }
      System.out.println("锁释放二次完成");
    }
    System.out.println("获取到的值:" + value);

    try {
      System.out.println("主线程等待中。。。。");
      Thread.sleep(30000);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.exit(0);
  }

}
