package com.flowable.springboot.bean;

import java.util.List;

/**
 * 用户类
 */
public class UserInfoEntity extends BaseEntity {
    private String userId;
    private String userName;
    private String userCode;
    private String password;
    private List<ProcTaskInfo> taskList;


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

    public List<ProcTaskInfo> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProcTaskInfo> taskList) {
        this.taskList = taskList;
    }
}
