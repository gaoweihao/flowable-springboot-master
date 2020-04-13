package com.flowable.springboot.requestBean;

import java.io.Serializable;
import java.util.List;

/**
 * 批量驳回流程参数
 * */
public class ProcessBackQuery implements Serializable {

    //当前操作人 Y
    private String userCode;

    //任务之间用逗号分割 Y
    private String taskIds;

    //财政驳回配置是在模型上预先配置好的，此参数值非必传 N
    private java.util.List<String> targetActivityIds;

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

    public List<String> getTargetActivityIds() {
        return targetActivityIds;
    }

    public void setTargetActivityIds(List<String> targetActivityIds) {
        this.targetActivityIds = targetActivityIds;
    }
}
