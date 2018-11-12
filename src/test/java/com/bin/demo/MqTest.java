package com.bin.demo;

import com.bin.demo.utils.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@SpringBootTest
public class MqTest {

    @Autowired
    private Sender sender;

    @Test
    public void testMq() {
        String msg = "aaaaaa";
        sender.send("test", msg);
    }
}
