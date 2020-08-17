package com.flowable.springboot.mogodb;

import com.flowable.springboot.bean.UserInfoEntity;

import java.util.List;


public interface MongodbDao extends MongodbBase<String,UserInfoEntity> {

//    /**
//     * 保存数据
//     */
//    void saveUser(UserInfoEntity user);
//
//    /**
//     * 根据字段移除数据
//     */
//    void removeUser(String id);
//
//    /**
//     * 修改数据
//     */
//    void updateUser(UserInfoEntity user);
//
//    /**
//     * 根据主键查询数据
//     */
//    UserInfoEntity findUserById(String id, Class<UserInfoEntity> clazz);
//
//    /**
//     * 查询所有数据
//     */
//    List<UserInfoEntity> findUserList(Class<UserInfoEntity> clazz);
//
//    /**
//     * 模糊查询数据
//     */
//    List<UserInfoEntity> findUserLikeList(String name,Class<UserInfoEntity> clazz);
//
//    /**
//     * 模糊查询数据
//     */
//    UserInfoEntity findAndModify(String id, UserInfoEntity user);
}
