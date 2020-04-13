package com.flowable.springboot.responseBean;

import java.io.Serializable;
/**
 * 批量启动、审核、驳回、撤销 返回参数类
 * */
public class FbpmResponseEntity implements Serializable {

	private static final long serialVersionUID = -8430577148324376348L;
	
	//任务Id
	private String taskId;
	
	//流程编号
	private String wfCode;
	
	//流程定义Id
	private String procDefId;
	
	//节点名称
	private String nodeId;
	
	//状态编码
	private String statusCode;
	
	//状态名称
	private String statusName;
	
	//节点上配置的isEnd属性
	private String isEnd;
	
	//业务单据Id
	private String businessKey;
	
	//流程实例Id
	private String procInstId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}
	
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
}
