package com.flowable.springboot.service.impl;

import com.flowable.springboot.annotation.Column;
import com.flowable.springboot.annotation.Id;
import com.flowable.springboot.annotation.Table;
import com.flowable.springboot.dao.BaseMapper;
import com.flowable.springboot.service.BaseService;
import com.flowable.springboot.util.CmsException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "baseService")
public class BaseServiceImpl implements BaseService {
    //分批保存阀值
    private int count = 1000;

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseMapper baseMapper;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private Map<String, Object> transformObj(Object t, String type) {
        //获取表名
        if (null == t.getClass().getAnnotation(Table.class)) {
            throw new CmsException("Error Input Object! Error @Table Annotation.");
        }
        Map<String, Object> re = new HashMap<String, Object>();
        re.put("TABLE_NAME", t.getClass().getAnnotation(Table.class).value());

        Method[] m = t.getClass().getMethods();
        if (m.length <= 0) {
            throw new CmsException("Error Input Object! No Method.");
        }
        //insert数据结构
        if ("insert".equals(type)) {
            List<String> k = new ArrayList<>();//存放列名
            List<Object> v = new ArrayList<>();//存放列值
            for (Method i : m) {
                //获取列名和值
                if (null != i.getAnnotation(Column.class)) {
                    k.add(i.getAnnotation(Column.class).value());
                    v.add(getInvokeValue(t, i));
                    continue;
                }
                if (null != i.getAnnotation(Id.class)) {
                    re.put("KEY_ID", i.getAnnotation(Id.class).value());
                    re.put("KEY_VALUE", getInvokeValue(t, i));
                }
            }
            if (k.size() != v.size()) {
                throw new CmsException("Error Input Object! Internal Error.");
            }
            re.put("COLUMNS", k);
            re.put("VALUES", v);
        } else if ("update".equals(type)) {
            //update数据结构
            List<Map<String, Object>> d = new ArrayList<>();
            for (Method i : m) {
                Map<String, Object> map = new HashMap<>();
                if (null != i.getAnnotation(Column.class) && null != getInvokeValue(t, i)) {
                    map.put("COLUMN", i.getAnnotation(Column.class).value());
                    map.put("COL_VALUE", getInvokeValue(t, i));
                    d.add(map);
                    continue;
                }
                if (null != i.getAnnotation(Id.class)) {
                    re.put("KEY_ID", i.getAnnotation(Id.class).value());
                    re.put("KEY_VALUE", getInvokeValue(t, i));
                }
            }
            re.put("DATA", d);
        } else if ("common".equals(type)) {
            //common数据结构
            for (Method i : m) {
                if (null != i.getAnnotation(Id.class)) {
                    re.put("KEY_ID", i.getAnnotation(Id.class).value());
                    re.put("KEY_VALUE", getInvokeValue(t, i));
                }
            }
        }
        return re;
    }

    private Object getInvokeValue(Object t, Method i) {
        try {
            return i.invoke(t, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CmsException("Error Input Object! Error Invoke Get Method.", e);
        }
    }

    @Override
    public int insert(Object obj) {
        Map<String, Object> params = transformObj(obj, "insert");
        log.info("Function Insert.Transformed Params:" + params);
        return baseMapper.insert(params);
    }

    @Override
    public HashMap query(long id, Class c) {
        Map<String, Object> params = new HashMap<>();
        try {
            params = transformObj(c.newInstance(), "common");
            log.info("Function Query.Transformed Params:" + params);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CmsException("Common Query Error.", e);
        }
        params.put("KEY_VALUE", id);
        return baseMapper.queryForObject(params);
    }

    @Override
    public int update(Object obj) {
        Map<String, Object> params = transformObj(obj, "update");
        log.info("Function Update.Transformed Params:" + params);
        return baseMapper.update(params);
    }

    @Override
    public int delete(Object obj) {
        Map<String, Object> params = transformObj(obj, "common");
        log.info("Function Delete.Transformed Params:" + params);
        return baseMapper.delete(params);
    }

    @Override
    public int batchInsert(String sqlId, List data) {
        /*通用批量保存，每count条分批commit*/
        if (data.isEmpty() || StringUtils.isEmpty(sqlId)) {
            log.info("批量保存出错，mapperIndex或data为空");
            return 0;
        }
        int result = 0, index = 0;
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        int lastIndex = count;
        try {
            while (index < data.size()) {
                if (data.size() <= lastIndex) {
                    lastIndex = data.size();
                    result = result + sqlSession.insert(sqlId, data.subList(index, lastIndex));
                    sqlSession.commit();
                    log.info("已保存[" + index + "--" + (lastIndex - 1) + "}条数据");
                    // 批量保存结束，退出循环
                    break;
                } else {
                    result = result + sqlSession.insert(sqlId, data.subList(index, lastIndex));
                    sqlSession.commit();
                    log.info("已保存[" + index + "--" + (lastIndex - 1) + "}条数据");
                    // 设置下一批
                    index = lastIndex;
                    lastIndex = lastIndex + count;
                }
            }
        } catch (Exception e) {
            if (null != sqlSession) {
                sqlSession.rollback();
            }
            throw new CmsException(e);
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
        return result;
    }

}
