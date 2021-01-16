package com.flowable.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.bean.UserInfoEntity1;
import com.flowable.springboot.dao.UserInfoDao;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void save(UserInfoEntity user) {
        UserInfoEntity1 user1 = new UserInfoEntity1();
        user1.setPassword(user.getPassword());
        user1.setUserCode(user.getUserCode());
        user1.setPassword(user.getPassword());
        String url ="http://localhost:8078/lcn-provider/user-info/save-user";
        BaseResponse response = restTemplate.postForObject(url, JSON.toJSON(user1),BaseResponse.class);
        if(null != response){
            System.out.println(response.getCode());
            System.out.println(response.getMsg());
            System.out.println(JSON.toJSONString(response.getData()));
        }
        //userInfoDao.save(user);
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

    @Override
    public Map<String, UserInfoEntity> findUserInfoMap() {
        List<UserInfoEntity> userList = userInfoDao.findList();
        Map<String,UserInfoEntity> userMap = new HashMap<>();
        userMap.computeIfAbsent("",k->new UserInfoEntity());
        return null;
    }


}
