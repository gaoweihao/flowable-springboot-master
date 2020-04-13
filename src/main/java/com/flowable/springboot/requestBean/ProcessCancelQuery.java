package com.flowable.springboot.requestBean;

import com.flowable.springboot.requestBean.variable.RestVariable;
import com.flowable.springboot.requestBean.variable.TaskFormRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 批量撤销流程传入参数
 * */
public class ProcessCancelQuery implements Serializable {

    //当前操作人 Y
    private String userCode;

    //待撤销的任务Id集合，任务之间用逗号分割 Y
    private String taskIds;

    //单个任务对应的审核参数 N
    private java.util.List<TaskFormRequest> taskFormRequests;

    //业务单据变量 N
    private java.util.List<RestVariable> variables;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public List<TaskFormRequest> getTaskFormRequests() {
        return taskFormRequests;
    }

    public void setTaskFormRequests(List<TaskFormRequest> taskFormRequests) {
        this.taskFormRequests = taskFormRequests;
    }

    public List<RestVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<RestVariable> variables) {
        this.variables = variables;
    }
}
