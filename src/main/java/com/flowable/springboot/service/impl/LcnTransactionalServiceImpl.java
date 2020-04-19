package com.flowable.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.dao.UserInfoDao;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.service.LcnTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service("lcnTransactionalService")
public class LcnTransactionalServiceImpl implements LcnTransactionalService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 远程调用保存user并查询user
     *
     * @param url  远程链接地址
     * @param user 用户user
     * @return response
     */
    @Override
    public BaseResponse saveAndOrUser(String url, UserInfoEntity user) {
        //远程保存user
        BaseResponse response = restTemplate.postForObject(url, JSON.toJSON(user), BaseResponse.class);
        //本地保存user
        user.setId(UUID.randomUUID().toString());
        user.setUserName(user.getUserName()+"1");
        userInfoDao.save(user);
        //远程查询user
        url = "http://localhost:8078/lcn-provider/user-info/select-user-list";
        response = restTemplate.getForObject(url, BaseResponse.class);
        List<UserInfoEntity> list = (List<UserInfoEntity>)response.getData();
        //查询本地user
        if(null != list && list.size() > 0){
            list.addAll(userInfoDao.findList());
        }else{
            response.setData(userInfoDao.findList());
        }
        return response;
    }


    /**
     * 查询并保存user
     *
     * @param user 用户user
     * @return response
     */
    @Override
    public BaseResponse selectAndSaveUser(UserInfoEntity user) {
        //远程查询user
        String url = "http://localhost:8078/lcn-provider/user-info/select-user-list";
        BaseResponse response = restTemplate.getForObject(url, BaseResponse.class);
        //远程保存user
        url = "http://localhost:8078/lcn-provider/user-info/save-user";
        response = restTemplate.postForObject(url, JSON.toJSON(user), BaseResponse.class);
        //本地保存user
        user.setId(UUID.randomUUID().toString());
        user.setUserName(user.getUserName()+"1");
        userInfoDao.save(user);
        List<UserInfoEntity> list = (List<UserInfoEntity>)response.getData();
        //查询本地user
        if(null != list && list.size() > 0){
            list.addAll(userInfoDao.findList());
        }else{
            response.setData(userInfoDao.findList());
        }
        return response;
    }

    /**
     * 查询-保存-查询user
     *
     * @param user 用户user
     * @return response
     */
    @Override
    public BaseResponse selectSaveSelectUser(UserInfoEntity user) {

        //远程查询user
        String url = "http://localhost:8078/lcn-provider/user-info/select-user-list";
        BaseResponse response = restTemplate.getForObject(url, BaseResponse.class);
        //远程保存user
        url = "http://localhost:8078/lcn-provider/user-info/save-user";
        response = restTemplate.postForObject(url, JSON.toJSON(user), BaseResponse.class);
        //本地保存user
        user.setId(UUID.randomUUID().toString());
        user.setUserName(user.getUserName()+"1");
        userInfoDao.save(user);
        //查询user
        url = "http://localhost:8078/lcn-provider/user-info/select-user-list";
        response = restTemplate.getForObject(url, BaseResponse.class);
        List<UserInfoEntity> list = (List<UserInfoEntity>)response.getData();
        //查询本地user
        if(null != list && list.size() > 0){
            list.addAll(userInfoDao.findList());
        }else{
            response.setData(userInfoDao.findList());
        }
        return response;
    }
}
