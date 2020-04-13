package com.flowable.springboot.requestBean;

/**
 * 已办任务传入参数
 *
 * */
public class TaskFinishQuery {

    //应用code(参数比传,值可不传) Y
    private String appCode;

    //已办人code Y
    private String userCode;

    //单据类型Code(多个单据类型之间用逗号分割) Y
    private String bizTypeCode;

    //菜单Id
    private String menuId;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getBizTypeCode() {
        return bizTypeCode;
    }

    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
