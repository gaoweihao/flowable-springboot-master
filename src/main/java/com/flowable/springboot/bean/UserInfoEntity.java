package com.flowable.springboot.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户类
 */
public class UserInfoEntity extends BaseEntity {
    private String userId;
    private String userName;
    private String userCode;
    private String password;
    private List<RoleBean> roles = new ArrayList<>();


    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleBean> roles) {
        this.roles = roles;
    }
}
