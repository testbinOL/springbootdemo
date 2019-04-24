package com.bin.demo.exception;

/**
 * @Author: xingshulin
 * @Date: 2019/4/11 上午11:15
 * @Description: 业务异常处理类
 * @Version: 1.0
 **/
public class BusinessException extends RuntimeException {

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  protected BusinessException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
