package com.flowable.springboot.flowable.deplyment;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowableTest {

    private ProcessEngine processEngine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;
    private ManagementService managementService;
    private DynamicBpmnService dynamicBpmnService;

    @Before
    public void flowableTest() {
        ProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test_flowable?serverTimezone=UTC&characterEncoding=UTF8")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //设置字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
        dynamicBpmnService = processEngine.getDynamicBpmnService();
        FormService formService = processEngine.getFormService();
        managementService = processEngine.getManagementService();
    }

    /**
     * 并行网关：多条分支
     * 1、每条分支均会走
     */
    @Test
    public void deployParallelGateway() {
        DeploymentBuilder deployment = repositoryService.createDeployment()
                .category("Parallel_Gateway1")
                .name("parallel_Gateway1")
                .addClasspathResource("processes/ParallelGateway1.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstanceByKey() {
        String processDefinitionKey = "Parallel_Gateway1";
        String businessKey = UUID.randomUUID().toString().replace("-", "");
        System.out.println(businessKey);
        String processDefinitionId = "Parallel_Gateway1:1:49b097d8-d78a-11ea-9ec6-24418c2db21c";
        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : taskList) {
            System.out.println(task.getId());
            System.out.println(task.getTaskDefinitionKey());
            System.out.println(task.getName());
        }
    }

    /**
     * 审核任务
     */
    @Test
    public void complete() {
        String taskId = "10003";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        System.out.println("-----------------审核任务开始---------------------");
        taskService.complete(taskId);
        System.out.println("-----------------审核任务结束---------------------");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task ruTask : taskList) {
            System.out.println(ruTask.getId());
            System.out.println(ruTask.getTaskDefinitionKey());
            System.out.println(ruTask.getName());
            System.out.println("---------------------------------------------------------");
        }
    }

    /**
     * 类似驳回
     */
    @Test
    public void moveActivityIdTo() {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId("2501")
                .moveActivityIdTo("sid-8206D9FE-D5EA-4512-B33C-338702E23CA8", "sid-83643F9A-EC7D-41F8-94E6-45E490346ACC")
                .changeState();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId("1").list();
        for (Task ruTask : taskList) {
            System.out.println(ruTask.getId());
            System.out.println(ruTask.getTaskDefinitionKey());
            System.out.println(ruTask.getName());
            System.out.println("---------------------------------------------------------");
        }
    }

    /**
     * 类似驳回
     */
    @Test
    public void processDefinitionEngineVersion() {
        int processDefinitionEngineVersion =2;
        List<Execution> list = runtimeService
                .createExecutionQuery().processDefinitionVersion(processDefinitionEngineVersion)
                .list();
        for (Execution execution : list) {
            System.out.println(execution.getId());
            System.out.println("---------------------------------");
        }
    }

    /**
     * 类似驳回
     */
    @Test
    public void saveExecutionEntity() {
        ExecutionEntityImpl executionEntity = new ExecutionEntityImpl();
        executionEntity.setId("12");
        executionEntity.setProcessInstanceId("12");
        executionEntity.setParentId("23we");
        executionEntity.setProcessDefinitionId("23we");
        executionEntity.setRootProcessInstanceId("23we");
        executionEntity.setActivityId("23we");
        executionEntity.setSuspensionState(1);
        executionEntity.setCountEnabled(true);
        executionEntity.setStartTime(new Date());
        executionEntity.setActive(false);
        executionEntity.setScope(false);

    }
}