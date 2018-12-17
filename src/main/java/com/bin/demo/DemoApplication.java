package com.bin.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /*@Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.setPort(8080);
        return factory;
    }*/
}
