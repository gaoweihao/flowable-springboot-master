package com.flowable.springboot.controller;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.service.LcnTransactionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lcn-transactional")
@Api(value = "lcn-transactional-controller",tags="验证lcn和本地事务")
public class LcnTransactionalController {

    public static final String lcnUrl = "http://localhost:8078/lcn-provider";

    @Autowired
    private LcnTransactionalService lcnTransactionalService;

    @Transactional
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    @PostMapping("/save-and-select-user")
    @ApiOperation(value="远程保存并查询user")
    public BaseResponse saveAndSelectUser(@RequestBody UserInfoEntity user) {
        String url = lcnUrl+"/user-info/save-user";
        BaseResponse response = lcnTransactionalService.saveAndOrUser(url,user);
        return response;
    }

    @Transactional
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    @PostMapping("/select-and-save-user")
    @ApiOperation(value="远程查询并保存user")
    public BaseResponse selectAndSaveUser(@RequestBody UserInfoEntity user) {
        BaseResponse response = lcnTransactionalService.selectAndSaveUser(user);
        return response;
    }

    @Transactional
    @LcnTransaction(propagation = DTXPropagation.REQUIRED)
    @PostMapping("/select-save-select-user")
    @ApiOperation(value="查询-保存-查询user")
    public BaseResponse selectSaveSelectUser(@RequestBody UserInfoEntity user) {
        BaseResponse response = lcnTransactionalService.selectSaveSelectUser(user);
        return response;
    }
}
