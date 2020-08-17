package com.flowable.springboot.service.impl;

import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.dao.UserInfoDao;
import com.flowable.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void save(UserInfoEntity user) {
        userInfoDao.save(user);
    }

    @Override
    public UserInfoEntity selectByUserId(String userId) {
        return userInfoDao.selectByUserId(userId);
    }

    @Override
    public UserInfoEntity findByUserCode(String userCode) {
        return userInfoDao.findByUserCode(userCode);
    }

    @Override
    public void saveList(List<UserInfoEntity> userList) {
        userInfoDao.saveList(userList);
    }
}
