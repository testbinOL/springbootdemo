package com.bin.demo.controller;

import com.bin.demo.domain.Greeting;
import com.bin.demo.utils.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("message")
public class MqController {

    private AtomicInteger count = new AtomicInteger();

    @Autowired
    private Sender sender;

    @RequestMapping("/sender/{message}")
    public void sendMessage(@PathVariable String message) {

        sender.send("test", new Greeting(count.getAndIncrement(), message));
    }

}
