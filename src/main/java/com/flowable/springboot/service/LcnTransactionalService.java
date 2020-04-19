package com.flowable.springboot.service;

import com.flowable.springboot.bean.UserInfoEntity;
import com.flowable.springboot.responseBean.BaseResponse;

public interface LcnTransactionalService {

    BaseResponse saveAndOrUser(String url, UserInfoEntity user);

    BaseResponse selectAndSaveUser(UserInfoEntity user);

    BaseResponse selectSaveSelectUser(UserInfoEntity user);
}
