package com.flowable.springboot.flowable.deplyment;

import org.flowable.common.engine.api.history.HistoricData;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.ProcessInstanceHistoryLog;
import org.flowable.engine.history.ProcessInstanceHistoryLogQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
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
public class HistoryTests {

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
        //processEngineConfiguration.setAsyncExecutorActivate(true);
        //processEngineConfiguration.setAsyncHistoryExecutorActivate(true);
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
                .category("leave_process")
                .name("leave_process")
                .addClasspathResource("processes/InclusiveGateway.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId() + " : " + deploy.getName());
    }


    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstanceByKey() {
        String processDefinitionKey = "leave_process";
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", "张三");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : taskList) {
            System.out.println(task.getId());
            System.out.println("------------------------------------------------------------------");
        }
    }

    /**
     * 查询历史数据
     * select distinct RES.* , DEF.KEY_ as PROC_DEF_KEY_, DEF.NAME_ as PROC_DEF_NAME_, DEF.VERSION_ as PROC_DEF_VERSION_, DEF.DEPLOYMENT_ID_ as DEPLOYMENT_ID_
     * from ACT_HI_PROCINST RES
     * left outer join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_
     * WHERE RES.END_TIME_ is not NULL order by RES.ID_ asc
     * */
    @Test
    public void createHistoricProcessInstanceQuery() {
        List<HistoricProcessInstance> instanceList = historyService.createHistoricProcessInstanceQuery().finished().list();
        for (HistoricProcessInstance instance : instanceList) {
            System.out.println(instance.getId());
            System.out.println(instance.getStartActivityId());
        }
    }

    /**
     * 查询历史活跃实例
     * select RES.* from ACT_HI_ACTINST RES order by RES.ID_ asc
     * */
    @Test
    public void createHistoricActivityInstanceQuery() {
        List<HistoricActivityInstance> instanceList = historyService.createHistoricActivityInstanceQuery().list();

        for (HistoricActivityInstance instance : instanceList) {
            System.out.println(instance.getActivityName());
            System.out.println(instance.getActivityId());
            System.out.println(instance.getEndTime());
        }
    }

    /**
     * 查询历史活跃实例
     * select distinct RES.* from ACT_HI_TASKINST RES order by RES.ID_ asc
     * */
    @Test
    public void createHistoricTaskInstanceQuery() {
        List<HistoricTaskInstance> instanceList = historyService.createHistoricTaskInstanceQuery().list();
        for (HistoricTaskInstance task : instanceList) {
            System.out.println(task.getId());
            System.out.println(task.getCreateTime());
            System.out.println(task.getEndTime());
        }
    }

    /**
     * 查询历史变量表
     * select RES.* from ACT_HI_VARINST RES order by RES.ID_ asc
     * */
    @Test
    public void createHistoricVariableInstanceQuery() {
        List<HistoricVariableInstance> instanceList = historyService.createHistoricVariableInstanceQuery().list();
        for (HistoricVariableInstance instance : instanceList) {
            System.out.println(instance.getVariableName());
            System.out.println(instance.getVariableTypeName());
            System.out.println(instance.getValue());
        }
    }

    /**
     * 查询历史日志表
     * */
    @Test
    public void createProcessInstanceHistoryLogQuery() {
        String processInstanceId ="20001";
        ProcessInstanceHistoryLog historyLog = historyService.createProcessInstanceHistoryLogQuery(processInstanceId)
                .includeActivities()
                .includeTasks()
                .singleResult();
        System.out.println(historyLog.getBusinessKey());
        System.out.println(historyLog.getProcessDefinitionId());
        List<HistoricData> historicData = historyLog.getHistoricData();
        for (HistoricData history : historicData) {
//            if(history instanceof HistoricActivityInstanceEntity){
//                HistoricActivityInstanceEntity activity = (HistoricActivityInstanceEntity)history;
//                System.out.println("----------------HistoricActivityInstanceEntity---------------");
//                System.out.println(activity.getIdPrefix());
//                System.out.println(activity.getActivityName());
//            }
            if(history instanceof HistoricTaskInstance){
                HistoricTaskInstance task = (HistoricTaskInstance)history;
                System.out.println("-------HistoricTaskInstance-------------");
                System.out.println(task.getId());
                System.out.println(task.getCreateTime());
            }
        }

    }

    /**
     * 查询历史权限表
     * */
    @Test
    public void getHistoricIdentityLinksForTask() {
        String taskId ="20010";
        List<HistoricIdentityLink> identityLinkList = historyService.getHistoricIdentityLinksForTask(taskId);
        for (HistoricIdentityLink identityLink : identityLinkList) {
            System.out.println(identityLink.getUserId());
        }
    }

    /**
     * 自定义sql查询数据
     * */
    @Test
    public void createNativeHistoricTaskInstanceQuery() {
        List<HistoricTaskInstance> list = historyService
                .createNativeHistoricTaskInstanceQuery()
                .sql("select * from act_hi_taskinst").list();
        for (HistoricTaskInstance task : list) {
            System.out.println(task.getId());
            System.out.println(task.getCreateTime());
            System.out.println(task.getTaskDefinitionKey());
            System.out.println(task.getName());
            System.out.println("--------------------------------------------");
        }


    }
}