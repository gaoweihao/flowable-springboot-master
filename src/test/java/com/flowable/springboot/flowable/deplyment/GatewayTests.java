package com.flowable.springboot.flowable.deplyment;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayTests {

    private ProcessEngine processEngine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;
    private ManagementService managementService;
    private DynamicBpmnService dynamicBpmnService;

    @Test
    public void contextLoads() {
    }

    @Before
    public void flowableTest() {
        ProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test_flowable?serverTimezone=UTC&characterEncoding=UTF8")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //部署资源的时候是否生成流程图偏
        //processEngineConfiguration.setCreateDiagramOnDeploy(true);
        //设置字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        //id生成器 增加1000
        //processEngineConfiguration.setIdBlockSize(1000);
        //uuId
        //processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
        processEngine = processEngineConfiguration.buildProcessEngine();//ProcessEngines.getDefaultProcessEngine();
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
     * 排他网关：只会走一条分支
     * 1、排他网关当只要有一条分支满足条件时，其他分支便不再使用
     * 2、排他网关过滤分支的顺序为xml文件中的 sequenceFlow 标签的顺序
     */
    @Test
    public void deployExclusiveGateway() {
        DeploymentBuilder deployment = repositoryService.createDeployment()
                .category("exclusive_Gateway")
                .name("exclusive_Gateway")
                .addClasspathResource("processes/ExclusiveGateway.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 包容网关:至少一条分支
     * 1、sequenceFlow 有条件时会根据条件判断，是否走该分支
     * 2、sequenceFlow 没有条件时候，默认走该分支
     */
    @Test
    public void deployInclusiveGateway() {
        DeploymentBuilder deployment = repositoryService.createDeployment()
                .category("inclusive_Gateway")
                .name("inclusive_Gateway")
                .addClasspathResource("processes/InclusiveGateway.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 并行网关：多条分支
     * 1、每条分支均会走
     */
    @Test
    public void deployParallelGateway() {
        DeploymentBuilder deployment = repositoryService.createDeployment()
                .category("parallel_Gateway")
                .name("parallel_Gateway")
                .addClasspathResource("processes/ParallelGateway.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstanceByKey() {
        String processDefinitionKey = "Parallel_Gateway";
        Map<String, Object> variables = new HashMap<>();
        //    variables.put("day", 6);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : taskList) {
            System.out.println(task.getId());
            System.out.println("-----20013---------------20016----------------------------------------------");
        }
    }

    @Test
    public void complete() {
        String taskId = "32509";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        taskService.complete(taskId);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task ruTask : taskList) {
            System.out.println(ruTask.getId());
            System.out.println("---------------------------------------------------------");
        }
    }

}