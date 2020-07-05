package com.flowable.springboot.deplyment;

import com.flowable.springboot.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuntimeTests {

  private ProcessEngine processEngine;
  private RepositoryService repositoryService;
  private RuntimeService runtimeService;
  private TaskService taskService;

    @Test
    public void contextLoads() {
    }

    @Before
    public void flowableTest(){
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
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
        FormService formService = processEngine.getFormService();
        IdentityService identityService = processEngine.getIdentityService();
        ManagementService managementService = processEngine.getManagementService();
        HistoryService historyService = processEngine.getHistoryService();
        String processEngineName = processEngine.getName();
    }

    @Test
    public void deploy(){
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("leave")
                .name("请假流程")
                .key("leave")
                .addClasspathResource("processes/leave_process.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId()+" : "+deploy.getName());
    }

    /**
     * 启动流程实例
     * */
    @Test
    public void startProcessInstanceByKey(){
        String processDefinitionKey = "leave_process";
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);
        System.out.println(processInstance.getId()+" : "+processInstance.getActivityId());
    }

    /**
     * 查询任务
     * */
    @Test
    public void createTaskQuery(){
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
     * */
    @Test
    public void completeTask(){
        String taskId = "22506";
        taskService.complete(taskId);
    }
}