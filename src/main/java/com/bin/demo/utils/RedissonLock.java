package com.bin.demo.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.util.concurrent.TimeUnit;
import com.bin.demo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: xingshulin Date: 2019/4/10 下午7:20
 *
 *
 * Description: Version: 1.0
 **/
@Component
@Slf4j
public abstract class RedissonLock {

  @Autowired
  private RedissonClient redissonClient;

  /**
   * description: 此方法在调用业务处理方式前，会先获取锁，相同的锁名参数可保证对同一资源的协调处理
   */
  protected void doBusinessLocked(String lockName, long waitTime) throws InterruptedException {
    Assert.notNull(lockName, "锁名不能为空");
    Assert.isTrue(waitTime >= 0, "锁等待时间不能小于零");
    RLock lock = redissonClient.getLock(lockName);
    boolean isLock = lock.tryLock(waitTime, TimeUnit.MILLISECONDS);
    if (isLock) {
      log.info("线程：" + Thread.currentThread().getName() + "获取锁[{}]成功", lockName);
      try {
        doBusiness();
      } catch (Exception e) {
        throw new BusinessException("业务处理异常", e);
      } finally {
        lock.unlock();
      }
    } else {
      log.info("线程：" + Thread.currentThread().getName() + "未获取到锁[{}]", lockName);
    }
  }

  /**
   * descrition：对共享资源的处理
   */
  protected abstract void doBusiness();

}
