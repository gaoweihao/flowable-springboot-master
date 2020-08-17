package com.flowable.springboot.mogodb.impl;

import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.mogodb.MongodbDao;
import org.springframework.stereotype.Component;

@Component
public class MongodbDaoImpl extends MongodbBaseImpl<String, UserInfoEntity> implements MongodbDao {
}
