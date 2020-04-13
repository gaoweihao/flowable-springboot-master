package com.flowable.springboot.service;

import org.flowable.task.api.Task;

import java.util.List;

public interface MyService {

    void startProcess(String assignee);

    List<Task> getTasks(String assignee);
}
