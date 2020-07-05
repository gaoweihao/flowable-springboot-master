package com.flowable.springboot;

//import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableDistributedTransaction
@MapperScan(value = "com.flowable.springboot.dao")//配置mapper扫描
@EnableEurekaClient
public class FlowableSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableSpringBootApplication.class, args);
    }
}

