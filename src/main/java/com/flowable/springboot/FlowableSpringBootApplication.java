package com.flowable.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.flowable.springboot.dao")//配置mapper扫描
//@EnableEurekaClient
public class FlowableSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableSpringBootApplication.class, args);
    }
}

