package com.flowable.springboot.requestBean.variable;


import java.util.List;

public class TaskFormRequest {

    private String taskId;

    private List<RestVariable> variables;

    public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<RestVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<RestVariable> variables) {
        this.variables = variables;
    }
}
