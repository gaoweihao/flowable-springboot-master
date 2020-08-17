package com.flowable.springboot.flowable.deplyment;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.job.api.Job;
import org.flowable.job.api.JobQuery;
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
public class ServiceTaskTests {

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

    @Test
    public void deploy() {
        DeploymentBuilder deployment = repositoryService.createDeployment()
                .category("service_Task")
                .name("service_Task")
                .addClasspathResource("processes/service_task2.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstanceByKey() {
        String processDefinitionKey = "service_task2";
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
    }

    @Test
    public void job() {
        List<Job> list = managementService.createJobQuery().elementId("").list();
        List<Job> list1 = managementService.createTimerJobQuery().list();
        System.out.println("---------------------------------------------------------");

    }

}