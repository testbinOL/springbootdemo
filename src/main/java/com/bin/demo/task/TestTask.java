package com.bin.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: xingshulin
 * @Date: 2018/12/13 上午10:58
 * @Description: TODO
 * @Version: 1.0
 **/
@Component
@Slf4j
public class TestTask {

    @Scheduled(cron = "0/60 * * * * ?")
    public void timeTask() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("当前时间：{}", simpleDateFormat.format(new Date()));
    }
}
