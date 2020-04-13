package com.flowable.springboot.responseBean;

/**
 * 待办/已办 任务返回参数
 * */
public class TaskStateResponseEntity {

    //待办任务Id
    private String taskId;

    //流程实例Id
    private String procInstId;

    //流程定义Id
    private String procDefId;

    //任务节点名称
    private String name;

    //任务节点Id
    private String nodeId;

    //待办任务创建时间
    private String createTime;

    //待办任务对应的单据Id
    private String businessKey;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
