package com.flowable.springboot.deplyment;

import com.flowable.springboot.cascade.DeleteProcessInstanceCascadeCmd;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.DataObject;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
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
public class RuntimeTests {

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
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
        FormService formService = processEngine.getFormService();
        managementService = processEngine.getManagementService();
        String processEngineName = processEngine.getName();
    }

    @Test
    public void deploy() {
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("leave")
                .name("请假流程")
                .key("leave")
                .tenantId("002")
                .addClasspathResource("processes/leave_process.bpmn20.xml");
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
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
    }

    /**
     * 查询任务
     */
    @Test
    public void createTaskQuery() {
        String processDefinitionKey = "leave_process";
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).list();
        for (Task task : taskList) {
            System.out.println(task.getProcessDefinitionId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getAssignee());
            System.out.println(task.getId());
            System.out.println("-------------------------------------------------------");
        }
    }

    /**
     * 审核任务
     */
    @Test
    public void completeTask() {
        String taskId = "22506";
        taskService.complete(taskId);
        System.out.println("------------------------------------------");
    }


    /**
     * 查询流程实例是否正在执行
     */
    @Test
    public void createProcessInstanceQuery() {
        String processInstId = "22501";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        if (null != processInstance) {
            System.out.println(processInstance.getName() + "正在执行！！！");
        }
    }

    /**
     * 查询执行实例
     */
    @Test
    public void createExecutionQuery() {
        List<Execution> executionList = runtimeService.createExecutionQuery().list();
        for (Execution execution : executionList) {
            System.out.println(execution.getId());
            System.out.println(execution.getActivityId());
            System.out.println("--------------------------------------------");
        }
    }

    /**
     * 查询历史流程实例
     * SELECT DISTINCT
     * RES.*,
     * DEF.KEY_ AS PROC_DEF_KEY_,
     * DEF.NAME_ AS PROC_DEF_NAME_,
     * DEF.VERSION_ AS PROC_DEF_VERSION_,
     * DEF.DEPLOYMENT_ID_ AS DEPLOYMENT_ID_
     * FROM
     * ACT_HI_PROCINST RES
     * LEFT OUTER JOIN ACT_RE_PROCDEF DEF ON RES.PROC_DEF_ID_ = DEF.ID_
     * WHERE
     * RES.PROC_INST_ID_ = ?
     * ORDER BY
     * RES.ID_ ASC
     */
    @Test
    public void createHistoricProcessInstanceQuery() {
        String processInstId = "22501";
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        if (null != historicProcessInstance) {
            System.out.println(historicProcessInstance.getId());
            System.out.println(historicProcessInstance.getStartActivityId());
            System.out.println(historicProcessInstance.getStartTime());
            System.out.println(historicProcessInstance.getEndTime());
        }
    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void createHistoricProcessInstanceQuery1() {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            System.out.println(historicProcessInstance.getId());
            System.out.println(historicProcessInstance.getStartActivityId());
            System.out.println(historicProcessInstance.getStartTime());
            System.out.println(historicProcessInstance.getEndTime());
            System.out.println("---------------------------------------");
        }
    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void createHistoricActivityInstanceQuery() {
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            System.out.println(historicActivityInstance.getActivityId());
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getActivityType());
            System.out.println("---------------------------------------------");
        }
    }

    /**
     * 查询历史任务
     */
    @Test
    public void createHistoricTaskInstanceQuery() {
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery().list();
        for (HistoricTaskInstance hiTask : taskInstanceList) {
            System.out.println(hiTask.getId());
            System.out.println(hiTask.getEndTime());
            System.out.println(hiTask.getCreateTime());
            System.out.println("------------------------------------------");
        }
    }

    /**
     * 设置流程实例发起人
     */
    @Test
    public void setAuthenticatedUserId() {
        String authenticatedUserId = "钱七";
        //设置流程发起人方式一
        //identityService.setAuthenticatedUserId(authenticatedUserId);
        //设置流程发起人方式二
        Authentication.setAuthenticatedUserId(authenticatedUserId);
        String processDefinitionKey = "leave_process";
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId() + " : " + processInstance.getActivityId());
    }

    /**
     * 测试参数
     */
    @Test
    public void getVariables() {
        //部署流程
        DeploymentBuilder deploymentBuilder = repositoryService
                .createDeployment()
                .key("dataObject_key")
                .category("dataObject_cate")
                .name("dateObject_name")
                .addClasspathResource("processes/dataObject_process.bpmn20.xml");
        String processDefinitionKey = "dataObject_process";
        //开启流程实例
        Authentication.setAuthenticatedUserId("dataObject_user");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        String processInstanceId = processInstance.getProcessInstanceId();
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId)
                .onlyChildExecutions().singleResult();
        String executionId = execution.getId();
        Map<String, DataObject> dataObjects = runtimeService.getDataObjects(executionId);
        System.out.println(dataObjects.size());
        System.out.println("----------------------------------------------------");
        dataObjects.forEach((key, data) -> {
            System.out.println(key);
            System.out.println(data.getName());
            System.out.println(data.getType());
            System.out.println(data.getValue());
        });
    }

    /**
     * 删除流程实例
     * 非级联删除
     */
    @Test
    public void deleteProcessInstance() {
        String processInstanceId = null;
        String deleteReason = null;
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    /**
     * 删除流程实例
     * 级联删除需要创建命令类
     */
    @Test
    public void deleteProcessInstanceCascade() {
        String processInstanceId = "32505";
        String deleteReason = "流程删除";
        managementService.executeCommand(new DeleteProcessInstanceCascadeCmd(processInstanceId, deleteReason));
    }

    /**
     * 获取运行活动的节点
     */
    @Test
    public void getActiveActivityIds() {
        String executionId = "45001";
        List<String> activeActivityIdList = runtimeService.getActiveActivityIds(executionId);
        for (String activeId : activeActivityIdList) {
            System.out.println(activeId);
        }
    }

    /**
     * 获取运行活动的节点
     */
    @Test
    public void startProcessInstanceById() {
        String processDefinitionId = "leave_process:2:ccc1aaba-be95-11ea-b153-24418c2db21c";
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        System.out.println(processInstance.getId());
    }

    /**
     * 获取运行活动的节点
     */
    @Test
    public void startProcessInstanceByKeyAndTenantId() {
        String processDefinitionKey = "leave_process";
        String tenantId = "002";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, tenantId);
        System.out.println(processInstance.getId());
    }

    /**
     * 查询流程定义是否挂起
     */
    @Test
    public void isProcessDefinitionSuspended() {
        String processDefinitionId = "leave_process:5:58bc4e85-c10e-11ea-b894-24418c2db21c";
        boolean suspended = repositoryService.isProcessDefinitionSuspended(processDefinitionId);
        System.out.println(suspended);
    }

    /**
     * 查询流程定义是否挂起
     */
    @Test
    public void suspendProcessDefinitionById() {
        String processDefinitionId = "leave_process:5:58bc4e85-c10e-11ea-b894-24418c2db21c";
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
        System.out.println("-------------------------------------------------");
        boolean suspended = repositoryService.isProcessDefinitionSuspended(processDefinitionId);
        System.out.println(suspended);
    }

    /**
     * 查询流程定义是否挂起
     */
    @Test
    public void activateProcessDefinitionById() {
        String processDefinitionId = "leave_process:5:58bc4e85-c10e-11ea-b894-24418c2db21c";
        repositoryService.activateProcessDefinitionById(processDefinitionId);
        System.out.println("-------------------------------------------------");
        boolean suspended = repositoryService.isProcessDefinitionSuspended(processDefinitionId);
        System.out.println(suspended);
    }

    /**
     * 挂起流程实例
     */
    @Test
    public void suspendProcessInstanceById() {
        String processInstanceId = "55001";
        runtimeService.suspendProcessInstanceById(processInstanceId);
        System.out.println("---------------------------------------------------");
    }


    /**
     * 激活流程实例
     */
    @Test
    public void activateProcessInstanceById() {
        String processInstanceId = "55001";
        runtimeService.activateProcessInstanceById(processInstanceId);
        System.out.println("---------------------------------------------------");
    }

}