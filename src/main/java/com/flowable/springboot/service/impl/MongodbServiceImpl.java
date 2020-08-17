package com.flowable.springboot.service.impl;

import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.mogodb.MongodbDao;
import com.flowable.springboot.service.MongodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mongodbService")
public class MongodbServiceImpl implements MongodbService {


    @Autowired
    private MongodbDao mogodbDao;

    /**
     * 保存数据
     * */
    @Override
    public void saveUser(UserInfoEntity user) {
        mogodbDao.saveUser(user);
    }

    /**
     * 根据字段移除数据
     * */
    @Override
    public void removeUser(String id) {
        mogodbDao.removeUser(id);
    }

    /**
     * 修改数据
     * */
    @Override
    public void updateUser(UserInfoEntity user) {
        mogodbDao.updateUser(user);
    }

    /**
     * 根据主键查询数据
     * */
    @Override
    public UserInfoEntity findUserById(String id) {
        return mogodbDao.findUserById(id,UserInfoEntity.class);
    }

    /**
     * 查询所有数据
     * */
    @Override
    public List<UserInfoEntity> findUserList() {
        return mogodbDao.findUserList(UserInfoEntity.class);
    }

    /**
     * 模糊查询数据
     * */
    @Override
    public List<UserInfoEntity> findUserLikeList(String name) {
        return mogodbDao.findUserLikeList(name,UserInfoEntity.class);
    }

    /**
     * 模糊查询数据
     * */
    @Override
    public UserInfoEntity findAndModify(UserInfoEntity user) {
        return mogodbDao.findAndModify(user.getId(),user);
    }
}
