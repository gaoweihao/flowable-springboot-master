package com.flowable.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.flowable.springboot.requestBean.*;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.responseBean.FbpmResponseEntity;
import com.flowable.springboot.responseBean.ProcTaskInfo;
import com.flowable.springboot.service.FbpmService;
import com.flowable.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ProcessStartQuery> requestEntity = new HttpEntity<>(processStart, headers);
//        ResponseEntity<BaseResponse<List<FbpmResponseEntity>>> result =
//                restTemplate.exchange(url, HttpMethod.POST,requestEntity, new ParameterizedTypeReference<BaseResponse<List<FbpmResponseEntity>>>(){});
//        BaseResponse<List<FbpmResponseEntity>> response = result.getBody();

        BaseResponse response = restTemplate.postForObject(url,JSON.toJSON(processStart),BaseResponse.class);
//        BaseResponse<List<FbpmResponseEntity>> s =
//                restTemplate.execute(url, HttpMethod.POST, requestEntity, BaseResponse<List<FbpmResponseEntity>>);
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
    @TxTransaction(propagation = DTXPropagation.REQUIRED)
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
    @TxTransaction(propagation = DTXPropagation.REQUIRED)
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
    @TxTransaction(propagation = DTXPropagation.SUPPORTS)
    public BaseResponse selectTaskListByState(String url, TaskStateQuery taskState) {
        BaseResponse response = new BaseResponse();
        url +="?userCode="+taskState.getAppCode()+
                "&roleId="+taskState.getRoleId()+
                "&bizTypeCode="+taskState.getBizTypeCode()+
                "&appCode="+taskState.getAppCode()+
                "&menuId="+taskState.getMenuId();
        String json = restTemplate.getForObject(url,String.class);
        List<ProcTaskInfo> procTaskInfo = (List<ProcTaskInfo>)JSON.parse(json);
        response.setCode(200);
        response.setData(procTaskInfo);
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
    @TxTransaction(propagation = DTXPropagation.SUPPORTS)
    public BaseResponse selectTaskFinishList(String url, TaskFinishQuery taskFinish) {
        BaseResponse response = new BaseResponse();
        url +="?userCode="+taskFinish.getAppCode()+
                "&bizTypeCode="+taskFinish.getBizTypeCode()+
                "&appCode="+taskFinish.getAppCode()+
                "&menuId="+taskFinish.getMenuId();
        String json = restTemplate.getForObject(url,String.class);
        List<ProcTaskInfo> procTaskInfo = (List<ProcTaskInfo>)JSON.parse(json);
        response.setCode(200);
        response.setData(procTaskInfo);
        return response;
    }

    /**
     * 根据流程实例废弃流程
     *
     * @param url        远程访问接口的路径
     * @return baseResponse
     */
    @Override
    public BaseResponse deleteProcessByBusinessKey(String url) {

        ResponseEntity<BaseResponse> resp =  restTemplate.exchange(url, HttpMethod.DELETE,null,BaseResponse.class);
        if(resp.getStatusCode().value() == 200){
            BaseResponse response = resp.getBody();
            System.out.println(response.getMsg());
        }
        return null;
    }
}
