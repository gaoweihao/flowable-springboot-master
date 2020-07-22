package com.flowable.springboot.deplyment;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NodeTests9 {

    private ProcessEngine processEngine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;
    private ManagementService managementService;

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
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
        FormService formService = processEngine.getFormService();
        managementService = processEngine.getManagementService();
    }

    @Test
    public void deploy() {
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("group_process_groups")
                .name("group_process_groups流程")
                .key("group_process_groups")
                .addClasspathResource("processes/group_process_groups.bpmn20.xml");
        //.addClasspathResource("processes/group_process1.bpmn20.xml");
        //.addClasspathResource("processes/业务流程测试.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstanceByKey() {
        String processDefinitionKey = "group_process_groups";
        //Map<String, Object> variables = new HashMap<>();
        //variables.put("userId", "张三,李四,王五,赵六");
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void trigger() {
        String processDefinitionKey = "receive-process";
        Execution execution = runtimeService
                .createExecutionQuery()
                .processDefinitionKey(processDefinitionKey)
                .onlyChildExecutions()
                .singleResult();
        runtimeService.trigger(execution.getId());
        System.out.println(execution.getActivityId());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void taskAssignee() {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("张三").list();
        if (null != taskList) {
            for (Task task : taskList) {
                System.out.println(task.getId());
                System.out.println(task.getProcessInstanceId());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    /**
     * 重新设置审核人
     */
    @Test
    public void setAssignee() {
        String taskId = "10006";
        String userId = "张三";
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 根据userId 查询任务
     * select distinct RES.* from ACT_RU_TASK RES
     * WHERE RES.ASSIGNEE_ is null and exists (
     *      select LINK.ID_ from ACT_RU_IDENTITYLINK LINK where LINK.TYPE_ = 'candidate' and LINK.TASK_ID_ = RES.ID_ and
     *      (
     *          LINK.USER_ID_ = ? or LINK.GROUP_ID_ IN ( ? )
     *      )
     * ) order by RES.ID_ asc
     */
    @Test
    public void findGroupTask() {
        String userId = "07a7fac20dfb4aae940362e3f61d48a4";
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateUser(userId).list();
        for (Task task : taskList) {
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getId());
            System.out.println("--------------------------------");
        }
    }
    /**
     * 根据角色id查询组任务
     */
    @Test
    public void taskCandidateGroup() {
        String candidateGroup = "68059036e6e6402b88f56178872dcdd3";
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateGroup(candidateGroup).list();
        for (Task task : taskList) {
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getId());
            System.out.println("--------------------------------");
        }
    }

    /**
     * 重新设置审核人
     */
    @Test
    public void getIdentityLinksForTask() {
        String taskId = "27506";
        List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : identityLinkList) {
            System.out.println(identityLink.getProcessDefinitionId());
            System.out.println(identityLink.getTaskId());
            System.out.println("-----------------------------");
        }
    }

    /**
     * 重新设置审核人
     */
    @Test
    public void getHistoricIdentityLinksForTask() {
        String taskId = "27506";
        List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(taskId);
        for (HistoricIdentityLink identityLink : historicIdentityLinksForTask) {
            System.out.println(identityLink.getProcessInstanceId());
            System.out.println(identityLink.getTaskId());
            System.out.println("-----------------------------");
        }
    }

    /**
     * 认领任务
     */
    @Test
    public void claim() {
        String taskId = "37507";
        String userId = "李琪";
        taskService.claim(taskId, userId);
    }

    /**
     * 保存用户组信息
     * */
    @Test
    public void saveGroups(){
        GroupEntityImpl group1 = new GroupEntityImpl();
        group1.setRevision(0);
        group1.setName("部门经理");
        group1.setId(getPrimary());
        identityService.saveGroup(group1);

        GroupEntityImpl group2 = new GroupEntityImpl();
        group2.setRevision(0);
        group2.setName("总经理");
        group2.setId(getPrimary());
        identityService.saveGroup(group2);

        UserEntityImpl user1 = new UserEntityImpl();
        user1.setRevision(0);
        user1.setDisplayName("张三");
        user1.setId(getPrimary());
        identityService.saveUser(user1);

        UserEntityImpl user2 = new UserEntityImpl();
        user2.setRevision(0);
        user2.setDisplayName("李四");
        user2.setId(getPrimary());
        identityService.saveUser(user2);

        UserEntityImpl user3 = new UserEntityImpl();
        user3.setRevision(0);
        user3.setDisplayName("王五");
        user3.setId(getPrimary());
        identityService.saveUser(user3);

        identityService.createMembership(user1.getId(),group1.getId());
        identityService.createMembership(user2.getId(),group1.getId());
        identityService.createMembership(user3.getId(),group2.getId());

    }

    public static String getPrimary(){
        return UUID.randomUUID().toString().replace("-","");
    }
}