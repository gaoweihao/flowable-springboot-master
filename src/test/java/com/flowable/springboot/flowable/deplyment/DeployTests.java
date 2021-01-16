package com.flowable.springboot.flowable.deplyment;

import com.flowable.springboot.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeployTests {

  private ProcessEngine processEngine;

  private RepositoryService repositoryService;
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
        processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
        processEngine = processEngineConfiguration.buildProcessEngine();//ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        TaskService taskService = processEngine.getTaskService();
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
        FormService formService = processEngine.getFormService();
        IdentityService identityService = processEngine.getIdentityService();
        ManagementService managementService = processEngine.getManagementService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HistoryService historyService = processEngine.getHistoryService();
        String processEngineName = processEngine.getName();
    }

    @Test
    public void deploymentBuild(){
        DeploymentBuilder deployment = repositoryService.createDeployment().category("测试分类").name("名称");
        deployment.addClasspathResource("processes/one-task-process.bpmn20.xml");
        System.out.println("deployment : "+ deployment);
    }

    @Test
    public void deploy(){
        DeploymentBuilder deployment = repositoryService.createDeployment().category("测试图片").name("测试图片");
        deployment.addClasspathResource("processes/one-task-process.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        System.out.println("deployId----------------"+deploy.getId());
    }

    /**
     * 文本方式部署
     * */
    @Test
    public void readString(){
        String text = IoUtil.readFileAsString("processes/one-task-process.bpmn20.xml");
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("测试分类")
                .name("名称")
                .key("readString_test")
                .addString("readString_test.bpmn20.xml",text);
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 读取流的方式部署
     * */
    @Test
    public void readInputStream(){
        InputStream in = DeployTests.class.getClassLoader().getResourceAsStream("processes/one-task-process.bpmn20.xml");
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("测试分类")
                .name("名称")
                .key("readInputStream_test")
                .addInputStream("readString_test.bpmn20.xml",in);
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 读压缩流的方式部署
     * （可以部署多个）
     * */
    @Test
    public void readZipInputStream(){
        InputStream in = DeployTests.class.getClassLoader().getResourceAsStream("processes/one-task-process.bpmn20.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("测试分类")
                .name("名称")
                .key("readZipInputStream_test")
                .addZipInputStream(zipInputStream);
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 读取字节
     * */
    @Test
    public void readBytes(){
        InputStream in = DeployTests.class.getClassLoader().getResourceAsStream("processes/one-task-process.bpmn20.xml");
        byte[] bytes = IoUtil.readInputStream(in,"one-task-process");
        DeploymentBuilder deployment = repositoryService
                .createDeployment()
                .category("测试分类")
                .name("名称")
                .key("readBytes_test")
                .addBytes("bytes",bytes);
        Deployment deploy = deployment.deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 流程定义相关查询
     * */
    @Test
    public void createProcessDefinitionQuery(){
        List<ProcessDefinition> procDefList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionWithoutTenantId()
                .list();
        for (ProcessDefinition procDef : procDefList) {
            System.out.println(procDef.getCategory());
            System.out.println(procDef.getName());
            System.out.println(procDef.getVersion());
            System.out.println("-----------------------------");
        }
    }

    /**
     * 删除流程
     * */
    @Test
    public void deleteDeployment(){
        String deploymentId ="";
        //非级联删除，只删除流程定义信息
        repositoryService.deleteDeployment(deploymentId);
        //级联删除，同时会删除流程实例
        repositoryService.deleteDeployment(deploymentId,true);

    }

    /**
     * 获取流程图片
     * */
    @Test
    public void getImg() throws IOException {
        String deploymentId ="12501";
        List<String> resourceNameList = repositoryService.getDeploymentResourceNames(deploymentId);
        String imgName = null;
        for (String name : resourceNameList) {
            if(name.contains(".png")){
                imgName =name;
                break;
            }
        }

      if(StringUtil.isNotEmpty(imgName)){
          File file = new File("");
          InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, imgName);
          FileUtils.copyInputStreamToFile(resourceAsStream,file);
      }
    }

    /**
     * 删除流程
     * */
    @Test
    public void createDeploymentQuery() {
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deploymentList) {
            System.out.println(deployment.getId());
            System.out.println(deployment.getName());
            System.out.println(deployment.getCategory());
            System.out.println("----------------------------------");
        }
    }

    /**
     * 删除流程
     * */
    @Test
    public void createNativeDeploymentQuery() {
        List<Deployment> deploymentList = repositoryService.createNativeDeploymentQuery()
                .sql("select * from act_re_deployment").list();
        for (Deployment deployment : deploymentList) {
            System.out.println(deployment.getId());
            System.out.println(deployment.getName());
            System.out.println(deployment.getCategory());
            System.out.println("----------------------------------");
        }
    }
}

