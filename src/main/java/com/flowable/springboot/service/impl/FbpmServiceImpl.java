package com.flowable.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.requestBean.*;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.service.FbpmService;
import com.flowable.springboot.service.UserInfoService;
import com.flowable.springboot.util.HttpUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service("fbpmService")
public class FbpmServiceImpl implements FbpmService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 批量启动流程
     *
     * @param url          远程访问接口的路径
     * @param processStart 批量启动流程所需参数
     * @return baseResponse
     */
    @Override
    @Transactional
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    public BaseResponse batchProcessInstance(String url, ProcessStartQuery processStart) {
        BaseResponse response = restTemplate.postForObject(url,JSON.toJSON(processStart),BaseResponse.class);
        return response;
    }

    /**
     * 批量审核流程
     *
     * @param url            远程访问接口的路径
     * @param processApprove 批量审核流程所需参数
     * @return baseResponse
     */
    @Override
    @Transactional
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    public BaseResponse batchProcessApprove(String url, ProcessApproveQuery processApprove) {
        BaseResponse response = restTemplate.postForObject(url,JSON.toJSON(processApprove),BaseResponse.class);
//            UserInfoEntity user = new UserInfoEntity();
//            user.setId(UUID.randomUUID().toString());
//            user.setUserCode("123456");
//            user.setUserName("张三si");
//            user.setPassword("123");
//            userInfoService.save(user);
//            if (Objects.nonNull(user)) {
//               // throw new IllegalStateException("by exFlag");
//            }

        return response;
    }

    /**
     * 批量驳回流程
     *
     * @param url         远程访问接口的路径
     * @param processBack 批量审核流程所需参数
     * @return baseResponse
     */
    @Override
    @Transactional
    @TxTransaction
    public BaseResponse batchProcessBack(String url, ProcessBackQuery processBack) {
        BaseResponse response = restTemplate.postForObject(url,JSON.toJSON(processBack),BaseResponse.class);
        return response;
    }

    /**
     * 批量撤销流程
     *
     * @param url           远程访问接口的路径
     * @param processCancel 批量撤销流程所需参数
     * @return baseResponse
     */
    @Override
    @Transactional
    @TxTransaction
    public BaseResponse batchProcessCancel(String url, ProcessCancelQuery processCancel) {
        BaseResponse response = restTemplate.postForObject(url,JSON.toJSON(processCancel),BaseResponse.class);
        return response;
    }

    /**
     * 查询代办任务
     *
     * @param url       远程访问接口的路径
     * @param taskState 批量审核流程所需参数
     * @return baseResponse
     */
    @Override
    @Transactional
    @TxTransaction
    public BaseResponse selectTaskListByState(String url, TaskStateQuery taskState) {
        BaseResponse response = restTemplate.getForObject(url,BaseResponse.class,JSON.toJSON(taskState));
        return response;
    }

    /**
     * 查询已办任务
     *
     * @param url        远程访问接口的路径
     * @param taskFinish 批量审核流程所需参数
     * @return baseResponse
     */
    @Override
    @Transactional
    @TxTransaction
    public BaseResponse selectTaskFinishList(String url, TaskFinishQuery taskFinish) {
        BaseResponse response = restTemplate.postForObject(url,JSON.toJSON(taskFinish),BaseResponse.class);
        return response;
    }
}
