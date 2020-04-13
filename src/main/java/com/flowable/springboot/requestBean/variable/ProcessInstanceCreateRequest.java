package com.flowable.springboot.requestBean.variable;

import java.util.List;

public class ProcessInstanceCreateRequest {

    //流程定义Id
    protected String processDefinitionId;
    //流程定义Key
    protected String processDefinitionKey;
    //消息
    protected String message;
    //任务id
    protected String name;
    //单据Id
    protected String businessKey;
    //业务单据变量(有分支的情况下必须传)
    protected List<RestVariable> variables;
    //瞬态单据变量
    protected List<RestVariable> transientVariables;
    //租户Id
    protected String tenantId;
    protected String overrideDefinitionTenantId;
    //单据类型
    protected String bizTypeCode;
    //区划
    protected String rgCode;
    // 单位
    protected String unitCode;
    //流程发起人
    protected String userId;
    // 流程发起人Code
    protected String userCode;
    // Added by Ryan Johnston
    private boolean returnVariables;

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public List<RestVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<RestVariable> variables) {
        this.variables = variables;
    }

    public List<RestVariable> getTransientVariables() {
        return transientVariables;
    }

    public void setTransientVariables(List<RestVariable> transientVariables) {
        this.transientVariables = transientVariables;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOverrideDefinitionTenantId() {
        return overrideDefinitionTenantId;
    }

    public void setOverrideDefinitionTenantId(String overrideDefinitionTenantId) {
        this.overrideDefinitionTenantId = overrideDefinitionTenantId;
    }

    public String getBizTypeCode() {
        return bizTypeCode;
    }

    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode;
    }

    public String getRgCode() {
        return rgCode;
    }

    public void setRgCode(String rgCode) {
        this.rgCode = rgCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public boolean isReturnVariables() {
        return returnVariables;
    }

    public void setReturnVariables(boolean returnVariables) {
        this.returnVariables = returnVariables;
    }
}
