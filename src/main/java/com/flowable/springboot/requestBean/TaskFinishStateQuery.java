package com.flowable.springboot.requestBean;

/**
 * 某状态下已办接口传入参数
 * */
public class TaskFinishStateQuery {

    //应用code(参数比传,值可不传) Y
    private String appCode;

    //已办人code Y
    private String userCode;

    //单据类型Code(多个单据类型之间用逗号分割) Y
    private String bizTypeCode;

    //菜单Id Y
    private String menuId;

    //角色Id Y
    private String roleId;

    //已办任务状态 Y
    private String state;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
