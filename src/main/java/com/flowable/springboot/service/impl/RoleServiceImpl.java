package com.flowable.springboot.service.impl;

import com.flowable.springboot.bean.RoleBean;
import com.flowable.springboot.dao.RoleDao;
import com.flowable.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public List<RoleBean> queryRolesByUserCode(String userCode) {

        return roleDao.queryRolesByUserCode(userCode);
    }
}
