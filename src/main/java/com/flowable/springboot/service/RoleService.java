package com.flowable.springboot.service;

import com.flowable.springboot.bean.RoleBean;

import java.util.List;

public interface RoleService {
    List<RoleBean> queryRolesByUserCode(String userCode);
}
