package com.flowable.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.responseBean.BaseResponse;
import com.flowable.springboot.responseBean.ProcEventRequestObj;
import com.flowable.springboot.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
@Api(value = "UserInfoController", tags = "用户管理")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PatchMapping("/user")
    @ApiOperation("保存用户信息")
    public void save(UserInfoEntity user) {
        userInfoService.save(user);
    }

    @GetMapping("/select-by-userId/{userId}")
    @ApiOperation(value = "通过用户主键查询用户")
    public BaseResponse selectByUserId(@PathVariable String userId) {
        UserInfoEntity user = userInfoService.selectByUserId(userId);
        return new BaseResponse(200, user);
    }

    @GetMapping("/event-info-get")
    @ApiOperation(value = "事件注册实测")
    public BaseResponse eventInfoGet() {
        System.out.println("事件注册测试---------------------------get");
        System.out.println("123");
        System.out.println("事件注册测试---------------------------get");
        return null;
    }

    @PostMapping("/event-info-post")
    @ApiOperation(value = "事件注册实测")
    public BaseResponse eventInfoPost(@RequestBody ProcEventRequestObj requestObj) {
        System.out.println("事件注册测试---------------------------Post");
        System.out.println(JSON.toJSONString(requestObj));
        System.out.println("事件注册测试---------------------------post");
        return null;
    }
}
