package com.flowable.springboot.service;

import com.flowable.springboot.bean.UserInfoEntity;

import java.util.List;

public interface MongodbService {
    
    void saveUser(UserInfoEntity user);

    void removeUser(String id);

    void updateUser(UserInfoEntity user);

    UserInfoEntity findUserById(String id);

    List<UserInfoEntity> findUserList();

    List<UserInfoEntity> findUserLikeList(String name);

    UserInfoEntity findAndModify(UserInfoEntity user);

}
