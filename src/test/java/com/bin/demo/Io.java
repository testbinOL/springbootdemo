package com.bin.demo;

/**
 * Author: xingshulin Date: 2019/4/4 下午2:29
 *
 *
 * Description: Version: 1.0
 **/
public class Io {

  public static void main(String[] args) {
    int j;
    while (true) {
      for (int i = 1; i < 10; i++) {
        if (i < 5) {
          j = 10 / i;
        } else {
          j = 5 / i;
        }
        System.out.println(j);
      }
    }
  }
}
