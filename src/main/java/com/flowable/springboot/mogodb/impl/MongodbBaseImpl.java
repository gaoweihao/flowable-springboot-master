package com.flowable.springboot.mogodb.impl;

import com.flowable.springboot.bean.BaseEntity;
import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.mogodb.MongodbBase;
import com.flowable.springboot.util.MongodbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;

public class MongodbBaseImpl<PK extends Serializable, T extends BaseEntity> implements MongodbBase<PK, T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(T user) {
        mongoTemplate.save(user);
    }

    @Override
    public void removeUser(PK pk) {
        mongoTemplate.remove(pk);
    }

    @Override
    public void updateUser(T t) {
        Query query = new Query(Criteria.where("id").is(t.getId()));
        Update update = MongodbUtil.getMongodbUpdate(t);
        FindAndModifyOptions options = new FindAndModifyOptions();
        // 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值
        options.upsert(true);
        // 返回当前最新值
        options.returnNew(true);
        UserInfoEntity seq = mongoTemplate.findAndModify(query, update, options, UserInfoEntity.class);
    }

    @Override
    public T findUserById(PK id,Class<T> clazz) {
        Query query = Query.query(Criteria.where("id").is(id));
        query.with(new Sort(Sort.Direction.DESC, "id")).limit(1);
        List<T> list = mongoTemplate.find(query,clazz);
        return list.get(0);
    }

    @Override
    public List<T> findUserList(Class<T> t) {
        List<T> userList = mongoTemplate.findAll(t);
        return userList;
    }

    @Override
    public List<T> findUserLikeList(String name,Class<T> t) {
        CriteriaDefinition criteria = Criteria.where("userName").regex(".*?\\" + name + ".*");
        Query query = Query.query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "id")).limit(1);
        List<T> list = mongoTemplate.find(query, t);
        return list;
    }


    @Override
    public T findAndModify(PK pk, T t) {
        return this.getQueryByPk(pk, t);
    }

    /**
     * 通过任意一字段更新数据
     *
     * @param pk 主键pk
     * @return t
     */
    public T getQueryByPk(PK pk, T obj) {
        Query query = new Query(Criteria.where("id").is(pk));
        Update update = MongodbUtil.getMongodbUpdate(obj);
        FindAndModifyOptions options = new FindAndModifyOptions();
        // 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值
        options.upsert(true);
        // 返回当前最新值
        options.returnNew(true);
        return (T)mongoTemplate.findAndModify(query, update, options, obj.getClass());
    }
}
