package com.bin.demo.utils;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: xingshulin Date: 2019/4/15 下午3:46
 *
 *
 * Description: zookeeper分布式锁 Version: 1.0
 **/
@Component
@Slf4j
public class ZookeeperLock implements Lock {

  //根节点
  private String ROOT_LOCK = "/locks";

  //锁名
  private String lockName;

  private String WAIT_LOCK;

  private String lockPath;

  private ZkClient client;

  public ZookeeperLock() {
  }

  public void build(String serverString, String lockPath) {
    this.lockPath = lockPath;
    this.client = new ZkClient(serverString);
    this.client.setZkSerializer(new MyZkSerializer());
  }

  public ZookeeperLock(String serverString, String lockPath) {
    super();
    build(serverString, lockPath);
  }

  @Override
  public void lock() {
    if (!tryLock()) {
      waitForLock();
      lock();
    }
  }

  private void waitForLock() {
    CountDownLatch cdl = new CountDownLatch(1);
    IZkDataListener listener = new IZkDataListener() {
      @Override
      public void handleDataChange(String s, Object o) throws Exception {
      }

      @Override
      public void handleDataDeleted(String s) throws Exception {
        cdl.countDown();
      }
    };
    client.subscribeDataChanges(lockPath, listener);
    if (client.exists(lockPath)) {
      try {
        cdl.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    client.unsubscribeDataChanges(lockPath, listener);
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
  }

  @Override
  public boolean tryLock() {
    try {
      client.createEphemeral(lockPath);
    } catch (ZkNodeExistsException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return false;
  }

  @Override
  public void unlock() {
    client.delete(lockPath);
  }

  @Override
  public Condition newCondition() {
    return null;
  }
}
