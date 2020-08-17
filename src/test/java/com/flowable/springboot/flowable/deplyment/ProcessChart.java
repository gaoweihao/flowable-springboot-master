package com.flowable.springboot.flowable.deplyment;

import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.job.api.Job;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessChart {

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


    /**
     * 生成普通的图片
     * BpmnModel bpmnModel,
     * String imageType,
     * List<String> highLightedActivities,
     * List<String> highLightedFlows,
     * String activityFontName,
     * String labelFontName,
     * String annotationFontName,
     * ClassLoader customClassLoader,
     * double scaleFactor,
     * boolean drawSequenceFlowNameWithNoLabelDI
     */
    @Test
    public void generateDiagram() throws IOException {
        String path = "D:/yonyou/1.jpg";
        String processDefinitionId = "Exclusive_Gateway:1:fb3f9f2c-ce44-11ea-b468-24418c2db21c";
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String imageType = "jpg";
        List<String> highLightedActivities = new ArrayList<>();//高亮显示的节点
        List<String> highLightedFlows = new ArrayList<>();//高亮显示的线
        String activityFontName = "宋体";
        String labelFontName = "宋体";
        String annotationFontName = "宋体";
        ClassLoader customClassLoader = null;
        double scaleFactor = 1.0D;
        boolean drawSequenceFlowNameWithNoLabelDI = true;
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator
                .generateDiagram(
                        bpmnModel,
                        imageType,
                        highLightedActivities,
                        highLightedFlows,
                        activityFontName,
                        labelFontName,
                        annotationFontName,
                        customClassLoader,
                        scaleFactor,
                        drawSequenceFlowNameWithNoLabelDI);
        FileUtils.copyInputStreamToFile(inputStream, new File(path));
    }

    /**
     * 生成高亮图片
     * */
    @Test
    public void generateDiagramHighLighted() throws IOException {
        String path = "D:/yonyou/1.jpg";
        String processDefinitionId = "Exclusive_Gateway:1:b933801e-d873-11ea-a1fd-24418c2db21c";
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String imageType = "jpg";
        List<String> highLightedActivities = new ArrayList<>();//高亮显示的节点
        highLightedActivities.add("sid-C548EF37-5847-45B1-8274-C11CAEBC7D03");
        highLightedActivities.add("sid-B54D174F-ABD7-43B0-B9F1-F614A411E710");
        List<String> highLightedFlows = new ArrayList<>();//高亮显示的线
        highLightedFlows.add("sid-BEA7594F-E823-4B7F-8EEF-5A18E90DE334");
        String activityFontName = "宋体";
        String labelFontName = "宋体";
        String annotationFontName = "宋体";
        ClassLoader customClassLoader = null;
        double scaleFactor = 1.0D;
        boolean drawSequenceFlowNameWithNoLabelDI = true;
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator
                .generateDiagram(
                        bpmnModel,
                        imageType,
                        highLightedActivities,
                        highLightedFlows,
                        activityFontName,
                        labelFontName,
                        annotationFontName,
                        customClassLoader,
                        scaleFactor,
                        drawSequenceFlowNameWithNoLabelDI);
        FileUtils.copyInputStreamToFile(inputStream, new File(path));
    }
}