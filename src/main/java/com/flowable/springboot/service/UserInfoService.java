package com.flowable.springboot.service;

import com.flowable.springboot.bean.UserInfoEntity;

public interface UserInfoService{

    void save(UserInfoEntity user);

    UserInfoEntity selectByUserId(String userId);

    UserInfoEntity findByUserCode(String username);
}
