package com.flowable.springboot.service;

import com.flowable.springboot.requestBean.*;
import com.flowable.springboot.responseBean.BaseResponse;

public interface FbpmService {

    BaseResponse batchProcessInstance(String taskUrl, ProcessStartQuery processStart) throws IllegalAccessException;

    BaseResponse batchProcessApprove(String url, ProcessApproveQuery processApprove);

    BaseResponse batchProcessBack(String url, ProcessBackQuery processBack);

    BaseResponse batchProcessCancel(String url, ProcessCancelQuery processCancel);

    BaseResponse selectTaskListByState(String url, TaskStateQuery taskStat);

    BaseResponse selectTaskFinishList(String url, TaskFinishQuery taskFinish);

}
