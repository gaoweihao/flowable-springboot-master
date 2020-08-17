package com.flowable.springboot.flowable.deplyment;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.job.api.Job;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobTests {

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
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setActivityFontName("宋体")
                .setLabelFontName("宋体")
                .setAnnotationFontName("宋体")
                .setAsyncExecutorActivate(true);
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
        Date date = new Date(new Date().getTime() + 2 * 1000);
        DeploymentBuilder deployment = repositoryService.createDeployment()
                .category("job_process")
                .name("job_process")
                .activateProcessDefinitionsOn(date)
                .addClasspathResource("processes/job_process.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstanceByKey() {
        String processDefinitionKey = "job_process";
        Map<String, Object> variables = new HashMap<>();
        //variables.put("userId", "张三");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : taskList) {
            System.out.println(task.getId());
            System.out.println("------------------------------------------------------------------");
        }
    }

    @Test
    public void sleep() throws InterruptedException {
        Thread.sleep(50000);
    }

    /**
     * 挂起流程
     */
    @Test
    public void suspendProcessDefinitionById() {
        Date date = new Date(new Date().getTime() + 2 * 1000);
        String processDefinitionId = "job_process:4:57504";
        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, date);

    }

    /**
     * 判断是否是5 版本的工作流
     */
    @Test
    public void isFlowable5ProcessDefinition() {
        Boolean isFlowable5 = repositoryService.isFlowable5ProcessDefinition("job_process:4:57504");
        System.out.println(isFlowable5);
    }

    /**
     * 判断是否是5 版本的工作流
     */
    @Test
    public void isProcessDefinitionSuspended() {
        Boolean isFlowable5 = repositoryService.isProcessDefinitionSuspended("job_process:4:57504");

        System.out.println(isFlowable5);
    }

    /**
     * 将定时任务表中的数据移动到任务表中
     */
    @Test
    public void moveTimerToExecutableJob() {
        String jobId = "";
        Job job = managementService.moveTimerToExecutableJob(jobId);
        System.out.println(job.getId());
        System.out.println(job.getCreateTime());
    }

    /**
     * 将job移动到死信作业表
     */
    @Test
    public void moveJobToDeadLetterJob() {
        String jobId = "";
        Job job = managementService.moveJobToDeadLetterJob(jobId);
        System.out.println(job);
    }

    /**
     * 将job从死信作业表移到执行作业表
     */
    @Test
    public void createHistoricProcessInstanceQuery() {
        String jobId = "";
        Job job = managementService.moveDeadLetterJobToExecutableJob(jobId, 3);
        System.out.println(job);
    }

    @Test
    public void executeJob() {
        String jobId = "";
        managementService.executeJob(jobId);
    }
}