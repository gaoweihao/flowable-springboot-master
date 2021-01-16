package com.flowable.springboot.service;

import com.flowable.springboot.bean.UserInfoEntity;

import java.util.List;
import java.util.Map;

public interface UserInfoService{

    void save(UserInfoEntity user);

    UserInfoEntity selectByUserId(String userId);

    UserInfoEntity findByUserCode(String username);

    void saveList(List<UserInfoEntity> userList);

    Map<String,UserInfoEntity> findUserInfoMap();
}
