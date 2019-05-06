package com.touna.test.springboot.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class BootstrapDubbo {

    public static void main(String[] args) {
        //启动springboot
        SpringApplication.run(BootstrapDubbo.class, args);
    }
}