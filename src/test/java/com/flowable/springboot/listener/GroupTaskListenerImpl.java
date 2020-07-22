package com.flowable.springboot.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class GroupTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.addCandidateUser("王五");
        delegateTask.addCandidateUser("王六");
        delegateTask.addCandidateUser("王七");
    }
}
