package com.flowable.springboot.dao;
import com.flowable.springboot.bean.RoleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
    List<RoleBean> queryRolesByUserCode(@Param("userCode") String userCode);
}
