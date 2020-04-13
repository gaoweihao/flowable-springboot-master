package com.flowable.springboot.requestBean;

import com.flowable.springboot.requestBean.variable.ProcessInstanceCreateRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 批量启动流程类
 * */
public class ProcessStartQuery implements Serializable {

    //当前操作人 Y
    private String userCode;

     // 批量启动参数 Y
    private List<ProcessInstanceCreateRequest> batchProcessInstanceCreateRequest;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<ProcessInstanceCreateRequest> getBatchProcessInstanceCreateRequest() {
        return batchProcessInstanceCreateRequest;
    }

    public void setBatchProcessInstanceCreateRequest(List<ProcessInstanceCreateRequest> batchProcessInstanceCreateRequest) {
        this.batchProcessInstanceCreateRequest = batchProcessInstanceCreateRequest;
    }
}
