package com.flowable.springboot;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowableSpringbootApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void flowableTest(){
        ProcessEngines.getDefaultProcessEngine();

        ProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test_flowable?serverTimezone=UTC&characterEncoding=UTF8")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();//ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
        FormService formService = processEngine.getFormService();
        IdentityService identityService = processEngine.getIdentityService();
        ManagementService managementService = processEngine.getManagementService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HistoryService historyService = processEngine.getHistoryService();
        String processEngineName = processEngine.getName();
        System.out.println("processEngineName : "+processEngineName);
        System.out.println("runtimeService : "+runtimeService);
        System.out.println("repositoryService : "+repositoryService);
        System.out.println("managementService : "+managementService);
        System.out.println("identityService : "+identityService);
        System.out.println("formService : "+ formService);
        System.out.println("dynamicBpmnService : "+ dynamicBpmnService);
        System.out.println("taskService : "+ taskService);
        System.out.println("processEngine : "+processEngine);
        System.out.println("historyService : "+historyService);
    }

}

