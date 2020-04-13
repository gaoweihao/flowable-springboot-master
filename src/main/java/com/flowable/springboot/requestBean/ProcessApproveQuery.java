package com.flowable.springboot.requestBean;

import com.flowable.springboot.requestBean.variable.TaskFormRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 审核传入参数类
 * */
public class ProcessApproveQuery implements Serializable {

    //当前操作人 Y
    private String userCode;

    //待办任务Id集合，任务之间用逗号分割 Y
    private String taskIds;

    //单个任务对应的审核参数 Y
    private List<TaskFormRequest> taskFormRequests;

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
}
