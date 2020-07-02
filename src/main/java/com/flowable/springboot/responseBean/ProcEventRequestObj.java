package com.flowable.springboot.responseBean;

import java.io.Serializable;

/**
 * @author lichao
 */
public class ProcEventRequestObj implements Serializable {
    /**
     * 任务Id
     */
    private String taskId;
    /**
     * 执行Id
     */
    private String exectionId;
    /**
     * 流程定义Id
     */
    private String procDefId;
    /**
     * 流程实例Id
     */
    private String procInstId;
    /**
     * 节点Id
     */
    private String nodeId;
    /**
     * 节点Id
     */
    private String nodeName;
    /**
     * 状态
     */
    private String status;
    /**
     * 操作类型
     * input提交 审核 next 驳回 back 撤销 cancel
     */
    private String operateType;
    /**
     * 单据Id
     */
    private String businessKey;
    /**
     * 单据类型Code
     */
    private String bizTypeCode;
    /**
     * 变量json串（传递参数变量）
     */
    private String jsonObj;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getJsonObj() {
        return jsonObj;
    }

    public void setJsonObj(String jsonObj) {
        this.jsonObj = jsonObj;
    }

    public String getExectionId() {
        return exectionId;
    }

    public void setExectionId(String exectionId) {
        this.exectionId = exectionId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getBizTypeCode() {
        return bizTypeCode;
    }

    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode;
    }
}
