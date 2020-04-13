package com.flowable.springboot.requestBean;

import com.flowable.springboot.requestBean.variable.RestVariable;

import java.io.Serializable;
import java.util.List;

/**
 * 自审核接口(财政帐户专用)
 * */
public class ReapProveTaskQuery implements Serializable {

    //待办任务Id Y
    private String taskId;

    //办理人code Y
    private String userCode;

    //单据变量集合 Y(name,type,vale 为必填)
    private java.util.List<RestVariable> variables;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<RestVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<RestVariable> variables) {
        this.variables = variables;
    }
}
