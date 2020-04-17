package com.flowable.springboot.bean;

/**
 * 用户类
 */
public class UserInfoEntity extends BaseEntity {
    private String userId;
    private String userName;
    private String userCode;
    private String password;


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

}
