package com.flowable.springboot.flowable.servictTask;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
@Component("serviceTask")
public class ServiceTask implements JavaDelegate {
    Expression age;

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println(age);
        System.out.println(age.getExpressionText());
        System.out.println(age.getValue(execution));
        execution.setVariable("user", "张三");
    }
}
