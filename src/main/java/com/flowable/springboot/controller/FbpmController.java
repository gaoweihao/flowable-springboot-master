package com.flowable.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.flowable.springboot.requestBean.*;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.responseBean.FbpmResponseEntity;
import com.flowable.springboot.service.FbpmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fbpm")
@Api(value = "fbpm-Controller",tags="fbpm 操作接口")
public class FbpmController {

    @Value("${task_url}")
    private String taskUrl;

    @Autowired
    private FbpmService fbpmService;

    @ApiOperation(value="fbpm批量启动流程")
    @PatchMapping("/fbpm-batch-process-instances")
    public BaseResponse batchProcessInstances(@RequestBody ProcessStartQuery processStart) throws IllegalAccessException {
        //批量启动远程接口
        String url = taskUrl+"/api/fbpm/process-instances/batch-create";
        BaseResponse response = fbpmService.batchProcessInstance(url,processStart);
        return response;

    }

    @ApiOperation(value="fbpm批量审核流程")
    @PatchMapping("/fbpm-batch-process-approve")
    public BaseResponse batchProcessApprove(@RequestBody ProcessApproveQuery processApprove){
        //批量启动远程接口
        String url = taskUrl+"/api/fbpm/tasks/batch-approve";
        BaseResponse response = fbpmService.batchProcessApprove(url,processApprove);
        List<FbpmResponseEntity> responseList = (List<FbpmResponseEntity>)response.getData();
        return response;

    }

    @ApiOperation(value="fbpm批量驳回流程")
    @PatchMapping("/fbpm-batch-process-back")
    public BaseResponse batchProcessBack(@RequestBody ProcessBackQuery processBack){
        //批量启动远程接口
        String url = taskUrl+"/api/fbpm/tasks/batch-back";
        BaseResponse response = fbpmService.batchProcessBack(url,processBack);
        List<FbpmResponseEntity> responseList = (List<FbpmResponseEntity>)response.getData();
        return response;

    }

    @ApiOperation(value="fbpm批量撤销流程")
    @PatchMapping("/fbpm-batch-process-cancel")
    public BaseResponse batchProcessCancel(@RequestBody ProcessCancelQuery processCancel){
        //批量启动远程接口
        String url = taskUrl+"/api/fbpm/tasks/batch-cancel";
        BaseResponse response = fbpmService.batchProcessCancel(url,processCancel);
        List<FbpmResponseEntity> responseList = (List<FbpmResponseEntity>)response.getData();
        return response;

    }

    @ApiOperation(value="fbpm查询待办任务接口")
    @PatchMapping("/fbpm-select-task-list-state/{state}")
    public BaseResponse selectTaskListByState(@PathVariable String state,@RequestBody TaskStateQuery taskStat){
        //批量启动远程接口
        String url = taskUrl+"/api/runtime/fbpm-user-state-task/taskList/"+state;
        BaseResponse response = fbpmService.selectTaskListByState(url,taskStat);
        return response;

    }

    @ApiOperation(value="fbpm查询已办任务接口")
    @PatchMapping("/fbpm-select-task-finish-list/state")
    public BaseResponse selectTaskFinishList(@PathVariable String state,@RequestBody TaskFinishQuery taskFinish){
        //批量启动远程接口
        String url = taskUrl+"/api/runtime/fbpm-user-finished-task/finishedTaskList";
        BaseResponse response = fbpmService.selectTaskFinishList(url,taskFinish);
        return response;

    }

    @ApiOperation(value="fbpm废弃流程")
    @DeleteMapping("/fbpm-delete-process/{businessKey}")
    public BaseResponse deleteProcessByBusinessKey(@PathVariable String businessKey){
        //批量启动远程接口
        String url = taskUrl+"/api/fbpm/process-instances/delete-by-businesskey/"+businessKey;
        BaseResponse response = fbpmService.deleteProcessByBusinessKey(url);
        List<FbpmResponseEntity> responseList = (List<FbpmResponseEntity>)response.getData();

        return response;

    }
}
