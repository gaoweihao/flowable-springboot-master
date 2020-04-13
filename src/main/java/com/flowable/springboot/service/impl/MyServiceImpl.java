package com.flowable.springboot.service.impl;

import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.dao.UserInfoDao;
import com.flowable.springboot.service.MyService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("myService")
@Transactional
public class MyServiceImpl implements MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Transactional
    public void startProcess(String assignee) {
        UserInfoEntity person = userInfoDao.findByUsername(assignee);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
    }

    /**
     * 查询任务列表
     * */
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

}
