package com.flowable.springboot.dao;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class ExpenseByPage {

    public String getList(Map<String, Object> param) {
        int pageSize = (int) param.get("pageSize");
        int pageNum = (int) param.get("pageNum");
        String deptName = (String) param.get("deptName");
        String type = (String) param.get("type");
        String sumFrom = String.valueOf(param.get("sumFrom"));
        String sumTo = String.valueOf(param.get("sumTo"));
        String dateFrom = String.valueOf(param.get("dateFrom"));
        String dateTo = String.valueOf(param.get("dateTo"));
        String userCode = (String) param.get("userCode");
        String processState = (String) param.get("processState");
        String select = " id,type_,deptName,reason,sum_,state as processState, status,username,date_ ";
        String from = " expenses ";
        StringBuffer where = new StringBuffer();
        where.append(" 1=1 ");
        if (StringUtils.isNotEmpty(deptName)) {
            where.append(" and deptName like" + "%" + deptName + "%");
        }
        if (StringUtils.isNotEmpty(sumFrom) && !sumFrom.equals("null")) {
            where.append(" and sum_ >=" + sumFrom);
        }
        if (StringUtils.isNotEmpty(sumTo) && !sumTo.equals("null")) {
            where.append(" and sum_<=" + sumTo);
        }
        if (StringUtils.isNotEmpty(dateFrom) && !dateFrom.equals("null")) {
            where.append(" and date_ >=" + dateFrom);
        }
        if (StringUtils.isNotEmpty(dateTo) && !dateTo.equals("null")) {
            where.append(" and date_<=" + dateTo);
        }
        //where.append(" and status ="+status);

        if (StringUtils.isNotEmpty(userCode)) {
            where.append(" and userCode=" + "'" + userCode + "'");
        }
        where.append(" and processState=0");
        BEGIN();
        SELECT(select);
        FROM(from);
        WHERE(where.toString());
        String sql = SQL();
        System.out.println("sql:" + sql);
        return sql;

    }

    public String getTaskList(Map<String, Object> param) {
        int pageSize = (int) param.get("pageSize");
        int pageNum = (int) param.get("pageNum");
        String deptName = (String) param.get("deptName");
        String type = (String) param.get("type");
        String sumFrom = String.valueOf(param.get("sumFrom"));
        String sumTo = String.valueOf(param.get("sumTo"));
        String dateFrom = String.valueOf(param.get("dateFrom"));
        String dateTo = String.valueOf(param.get("dateTo"));
        String userCode = (String) param.get("userCode");
        String processState = (String) param.get("processState");
        String inProcessIds = (String) param.get("inProcessIds");
        String select = " id,type_,dept_Name,reason,sum_,state as process_State, status,user_name,date_,process_Id ";
        String from = " expenses ";
        StringBuffer where = new StringBuffer();
        where.append(" 1=1 ");
        if (StringUtils.isNotEmpty(deptName)) {
            where.append(" and dept_Name like" + "%" + deptName + "%");
        }
        if (StringUtils.isNotEmpty(sumFrom) && !sumFrom.equals("null")) {
            where.append(" and sum_ >=" + sumFrom);
        }
        if (StringUtils.isNotEmpty(sumTo) && !sumTo.equals("null")) {
            where.append(" and sum_<=" + sumTo);
        }
        if (StringUtils.isNotEmpty(dateFrom) && !dateFrom.equals("null")) {
            where.append(" and date_ >=" + dateFrom);
        }
        if (StringUtils.isNotEmpty(dateTo) && !dateTo.equals("null")) {
            where.append(" and date_<=" + dateTo);
        }
        //where.append(" and status ="+status);
        where.append(" and process_Id in " + inProcessIds);

        BEGIN();
        SELECT(select);
        FROM(from);
        WHERE(where.toString());
        String sql = SQL();
        System.out.println("sql:" + sql);
        return sql;

    }

}
