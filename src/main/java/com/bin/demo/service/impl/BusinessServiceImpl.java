package com.bin.demo.service.impl;

import org.redisson.api.RScript.Mode;
import org.redisson.api.RScript.ReturnType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import com.bin.demo.service.BusinessService;
import com.bin.demo.utils.RedissonLock;
import com.bin.demo.utils.ZookeeperLock;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: xingshulin Date: 2019/4/11 上午11:47
 *
 *
 * Description: 业务处理类 Version: 1.0
 **/
@Service
@Slf4j
public class BusinessServiceImpl extends RedissonLock implements BusinessService {

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private ZookeeperLock zookeeperLock;

  @Override
  protected void doBusiness() {
    log.info("业务处理开始");
    String warehouse = "warehouse";
    String command = "local value = redis.call('get',KEYS[1]); if (tonumber(value) > 0) then local result = redis.call('decr',KEYS[1]); return result;end;return 0";
    Long result = redissonClient.getScript()
        .eval(Mode.READ_WRITE, command, ReturnType.INTEGER, Collections.singletonList(warehouse));
    if (result >= 0) {
      log.info("{}:库存：{}", Thread.currentThread().getName(), result);
    } else {
      log.info("库存不足");
    }
    log.info("业务处理完成");
  }

  @Override
  public void reduceInventory() {
    log.info(Thread.currentThread().getName() + ":start to reduce:" + System.currentTimeMillis());
    String lockName = "test_lock";
    try {
      doBusinessLocked(lockName, 10000);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    log.info(Thread.currentThread().getName() + ":reduce over");
  }

  @Override
  public void reduce() {
    log.info(Thread.currentThread().getName() + ":start to reduce:" + System.currentTimeMillis());
    String lockName = "test_lock";
    zookeeperLock.build("localhost:2181", "/temp/" + lockName);
    try {
      zookeeperLock.lock();
      doBusiness();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      zookeeperLock.unlock();
    }
    log.info(Thread.currentThread().getName() + ":reduce over");
  }
}
