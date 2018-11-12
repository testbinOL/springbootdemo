package com.bin.demo.utils;

import com.bin.demo.domain.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {

    @RabbitListener(queues = "${rabbitmq.queueName}")
    public void precess(Greeting msg) {
        log.info("receive:{}", msg);
    }

}

