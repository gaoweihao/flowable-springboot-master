package com.flowable.springboot.controller;

import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.service.MongodbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongodb")
@Api(value = "MongoDBController", tags = "mongodb")
public class MongoDBController {

    @Autowired
    private MongodbService mongodbService;

    @PostMapping("/save-user")
    @ApiOperation(value = "mongodb 测试保存用户")
    public void saveUser(@RequestBody UserInfoEntity user){

        mongodbService.saveUser(user);
    }

    @DeleteMapping("/remove-user/{id}")
    @ApiOperation(value = "mongodb 测试移除用户")
    public void removeUser(@PathVariable String id){
        mongodbService.removeUser(id);
    }

    @PostMapping("/update-user/{id}")
    @ApiOperation(value = "mongodb 测试更新用户")
    public void updateUser(@RequestBody UserInfoEntity user){
        mongodbService.updateUser(user);
    }

    @PostMapping("/find-user/{id}")
    @ApiOperation(value = "mongodb 测试查询用户")
    public UserInfoEntity findUserById(@PathVariable String id){
       return mongodbService.findUserById(id);
    }

    @PostMapping("/find-user-list")
    @ApiOperation(value = "mongodb 查询所有用户")
    public List<UserInfoEntity> findUserList(){
        return mongodbService.findUserList();
    }

    @PostMapping("/find-user-like-list/{name}")
    @ApiOperation(value = "mongodb 查询所有用户")
    public List<UserInfoEntity> findUserLikeList(@PathVariable String name){
        return mongodbService.findUserLikeList(name);
    }

    @PostMapping("/find-user-modify")
    @ApiOperation(value = "mongodb 查询所有用户")
    public UserInfoEntity findUserLikeList(@RequestBody UserInfoEntity user){
        return mongodbService.findAndModify(user);
    }

}
