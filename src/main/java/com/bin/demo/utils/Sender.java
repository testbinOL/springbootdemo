package com.bin.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String queueName, Object msg) {
        log.info("Send info: {}", msg);
        this.amqpTemplate.convertAndSend(queueName, msg);
    }
}
