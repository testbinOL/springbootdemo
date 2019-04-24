package com.bin.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author: xingshulin Date: 2019/4/19 下午2:32
 *
 *
 * Description: TODO Version: 1.0
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

  @Autowired
  private RedissonClient redissonClient;

  @Test
  public void redisTest() {

    int threadNum = 3000;

    ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
    CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    System.out.println("开始执行写入");

    long start = System.currentTimeMillis();
    for (int i = 0; i < threadNum; i++) {
      executorService.submit(() -> {
        try {
          countDownLatch.await();
        } catch (InterruptedException e) {
          System.out.println("超时：" + e.getMessage());
        }
        RBucket<Integer> rBucket = redissonClient.getBucket(Thread.currentThread().getName());
        rBucket.set(1);
      });
      countDownLatch.countDown();
    }
    executorService.shutdown();
    try {
      executorService.awaitTermination(100, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long times = System.currentTimeMillis() - start;
    System.out.println("耗时：" + times);
  }

}
