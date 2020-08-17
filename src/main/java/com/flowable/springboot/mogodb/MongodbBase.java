package com.flowable.springboot.mogodb;

import com.flowable.springboot.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface MongodbBase<PK extends Serializable, T extends BaseEntity> {

    void saveUser(T user);

    void removeUser(PK id);

    void updateUser(T user);

    T findUserById(PK pk,Class<T> clazz);

    List<T> findUserList(Class<T> t);

    List<T> findUserLikeList(String name,Class<T> t);

    T findAndModify(PK pk, T t);
}
