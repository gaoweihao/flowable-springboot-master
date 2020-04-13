package com.flowable.springboot.bean;

import java.util.List;

/**
 * 报销基本信息
 * @author Administrator
 *
 */
public class ExpenseEntity extends BaseEntity {

	private static final long serialVersionUID = 821408903501083870L;
	
	private String type;
	
	private String deptName;
	
	private String reason;
	
	private double sum;
	
	/**
	 * 票据状态
	 */
	private int state;
	
	/**
	 * 流程状态
	 */
	private int processState;
	
	private int status;
		
	private String userName;
	
	/**
	 * 日期
	 */
	private long date;
	
	/**
	 * 明细
	 */
	List<ExpenseDetail> items;
	
	/**
	 * 登录人code
	 */
	private String userCode;
	
	/**
	 * 流程实例Id
	 */
	private String processId;
	
	private String taskId;
	
	private String nodeId;
	
	private String procDefId;

	/**
	 * 创建日期
	 */
	private long createDate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getProcessState() {
		return processState;
	}

	public void setProcessState(int processState) {
		this.processState = processState;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public List<ExpenseDetail> getItems() {
		return items;
	}

	public void setItems(List<ExpenseDetail> items) {
		this.items = items;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
}
