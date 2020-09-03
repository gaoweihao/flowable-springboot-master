package com.flowable.springboot.controller;

import com.flowable.springboot.rabbitmq.MsgProducer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@Api(value = "SwaggerValue", tags = {"RabbitMQController"})
public class RabbitMQController {

    @Autowired
    private MsgProducer msgProducer;

    @GetMapping(value = "/sendFanout")
    @Transactional(rollbackFor = Exception.class)
    public void sendMsg() {
        msgProducer.send2FanoutTestQueue("this is a test fanout message!");
    }

    @GetMapping(value = "/sendDirect")
    @Transactional(rollbackFor = Exception.class)
    public void sendDirectMsg() {
        msgProducer.send2DirectTestQueue("this is a test direct message!");
    }

    @GetMapping(value = "/sendDirectA")
    @Transactional(rollbackFor = Exception.class)
    public void sendTopicAMsg() {
        msgProducer.send2TopicTestAQueue("this is a test topic aaa message!");
    }

    @GetMapping(value = "/sendTopicB")
    @Transactional(rollbackFor = Exception.class)
    public void sendTopicBMsg() {
        msgProducer.send2TopicTestBQueue("this is a test topic bbb message!");
    }
}
