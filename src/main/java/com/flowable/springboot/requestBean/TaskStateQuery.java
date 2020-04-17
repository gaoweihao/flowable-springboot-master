package com.flowable.springboot.requestBean;

/**
 * 待办任务传入参数
 * */
public class TaskStateQuery {

    //应用code(参数比传,值可不传) Y
    private String appCode;

    //待办人code Y
    private String userCode;

    //角色Id(多个角色Id之间用逗号分割) Y
    private String roleId;

    //单据类型Code(多个单据类型之间用逗号分割) Y
    private String bizTypeCode;

    //菜单Id Y
    private String menuId;

    //任务状态码(多个状态码之间用逗号分割) Y
    //private String state;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
}
