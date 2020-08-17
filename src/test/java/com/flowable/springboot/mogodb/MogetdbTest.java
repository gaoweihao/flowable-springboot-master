package com.flowable.springboot.mogodb;

import com.flowable.springboot.bean.UserInfoEntity;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MogetdbTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    private MongoClient mongoClient;

    @Before
    public void getMogodbConn() {
        MongoClientOptions.Builder buide = new MongoClientOptions.Builder();
        buide.connectionsPerHost(100);// 与目标数据库可以建立的最大链接数
        buide.connectTimeout(1000 * 60 * 20);// 与数据库建立链接的超时时间
        buide.maxWaitTime(100 * 60 * 5);// 一个线程成功获取到一个可用数据库之前的最大等待时间
        buide.threadsAllowedToBlockForConnectionMultiplier(100);
        buide.maxConnectionIdleTime(0);
        buide.maxConnectionLifeTime(0);
        buide.socketTimeout(0);
        MongoClientOptions myOptions = buide.build();
        //mongoClient = new MongoClient(new ServerAddress("10.1.3.127", 27017), myOptions);
        //连接数据库，如果该数据库在本地，可写为new ServerAddress(“localhost”,27017);
        mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), myOptions);
    }

    @Test
    private void mogodb() {

    }



}
