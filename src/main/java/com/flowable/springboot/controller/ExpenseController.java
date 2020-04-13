package com.flowable.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.flowable.springboot.bean.ExpenseEntity;
import com.flowable.springboot.service.ExpenseService;
import com.flowable.springboot.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expense")
@Api(value = "ExpenseController",tags = "财务demo示例")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private RoleService roleService;

    @Value("${task_url}")
    private String taskUrl;

    /**
     * 申请列表
     *
     * @param param
     * @return
     */
    @RequestMapping("/listByPageSaved")
    @ResponseBody
    public Object listExpenseEntityBypage(@RequestBody Map<String, Object> param) {
        List<ExpenseEntity> list = expenseService.listExpenseEntityByPage((String) param.get("userCode"));
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("rows", list);
        resp.put("total", list.size());
        return JSON.toJSONString(resp);
    }

    /**
     * 代办列表
     *
     * @param param
     * @return
     */
    @RequestMapping("/listByPageTask")
    @ResponseBody
    public Object listExpenseEntityBypageTask(@RequestBody Map<String, Object> param) {
//        UserInfoEntity user = (UserInfoEntity) SecurityUtils.getSubject().getPrincipal();
//        String userCode = user.getUserCode();
//        StringBuffer roleId = new StringBuffer();
//
//        List<RoleBean> roles = roleService.queryRolesByUserCode(userCode);
//        if (roles != null && roles.size() != 0) {
//            for (int i = 0; i < roles.size(); i++) {
//                roleId.append(roles.get(i).getId()).append(",");
//            }
//
//        }
//        if (roleId.length() > 0) {
//            roleId.deleteCharAt(roleId.length() - 1);
//        }
//
//        String appCode = (String) param.get("appCode");
//        String bizTypeCode = (String) param.get("bizTypeCode");
//        //String task_url = "http://10.16.21.29:9000/api/runtime/fbpm-user-task/"+userCode;
//        String task_url = taskUrl + "/fbpm-process/api/runtime/fbpm-user-task/taskList?userCode=" + userCode + "&roleId=" + roleId.toString() + "&appCode=" + appCode + "&bizTypeCode=" + bizTypeCode;
//        String res = HttpUtil.doGet(task_url, null);
//        List<ProcTaskInfo> taskList = JSON.parseArray(res, ProcTaskInfo.class);
//        //List<String> processIns = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        if (taskList != null && !taskList.isEmpty()) {
//            for (ProcTaskInfo info : taskList) {
//                sb.append(info.getBusinessKey());
//                sb.append(",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//        }
//        Integer state = (Integer) param.get("state");
//        List<ExpenseEntity> list;
//        if (state == 0) {
//            // 未提交标签页查询
//            list = expenseService.queryExpenses(userCode, sb.toString().split(","));
//        } else {
//            // 未审批标签页查询
//            list = expenseService.queryExpenses2(sb.toString().split(","));
//        }
//        List<ExpenseEntity> resultList = new ArrayList<>();
//        for (ExpenseEntity entity : list) {
//            for (ProcTaskInfo info : taskList) {
//                if (info.getBusinessKey().equals(entity.getId())) {
//                    entity.setTaskId(info.getTaskId());
//                    entity.setNodeId(info.getNodeId());
//                    entity.setProcDefId(info.getProcDefId());
//                    entity.setProcessId(info.getProcInstId());
//                    break;
//                }
//            }
//            resultList.add(entity);
//        }
//        Map<String, Object> resp = new HashMap<String, Object>();
//        resp.put("rows", resultList);
//        resp.put("total", resultList.size());
//        return JSON.toJSONString(resp);
        return null;
    }

    /**
     * 已办列表
     *
     * @param param
     * @return
     */
    @RequestMapping("/listByPageFinishedTask")
    @ResponseBody
    public Object listExpenseEntityBypageFinishedTask(@RequestBody Map<String, Object> param) {
//        UserInfoEntity user = (UserInfoEntity) SecurityUtils.getSubject().getPrincipal();
//        String userCode = user.getUserCode();
//        String roleId = "";
//
//        List<RoleBean> roles = roleService.queryRolesByUserCode(userCode);
//        if (roles != null && roles.size() != 0) {
//            roleId = roles.get(0).getId();
//
//        }
//        String appCode = (String) param.get("appCode");
//        String bizTypeCode = (String) param.get("bizTypeCode");
//        //String task_url = "http://10.16.21.29:9000/api/runtime/fbpm-user-task/"+userCode;
//        String task_url = taskUrl + "/fbpm-process/api/runtime/fbpm-user-task/finishedTaskList?userCode=" + userCode + "&roleId=" + roleId + "&appCode=" + appCode + "&bizTypeCode=" + bizTypeCode + "&state=014,010";
//        String res = HttpUtil.doGet(task_url, null);
//        List<ProcTaskInfo> taskList = JSON.parseArray(res, ProcTaskInfo.class);
//        //List<String> processIns = new ArrayList<>();
//        StringBuffer sb = new StringBuffer();
//        if (taskList != null && taskList.size() > 0) {
//            for (ProcTaskInfo info : taskList) {
//                sb.append(info.getBusinessKey());
//                sb.append(",");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            List<ExpenseEntity> list = expenseService.queryExpenses2(sb.toString().split(","));
//            List<ExpenseEntity> resultList = new ArrayList<>();
//            for (ExpenseEntity entity : list) {
//                for (ProcTaskInfo info : taskList) {
//                    if (info.getBusinessKey().equals(entity.getId())) {
//                        entity.setTaskId(info.getTaskId());
//                        entity.setNodeId(info.getNodeId());
//                        entity.setProcDefId(info.getProcDefId());
//                        entity.setProcessId(info.getProcInstId());
//                        break;
//                    }
//                }
//                resultList.add(entity);
//            }
//            Map<String, Object> resp = new HashMap<String, Object>();
//            resp.put("rows", resultList);
//            resp.put("total", resultList.size());
//            return JSON.toJSONString(resp);
//        } else {
//            List<ExpenseEntity> resultList = new ArrayList<>();
//            Map<String, Object> resp = new HashMap<String, Object>();
//            resp.put("rows", resultList);
//            resp.put("total", resultList.size());
//            return JSON.toJSONString(resp);
//        }
        return null;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("id") String id) {
        ModelAndView mav = new ModelAndView();
        ExpenseEntity entity = expenseService.getExpenseEntityById(id);
        mav.addObject("entity", entity);
        mav.setViewName("detail");
        return mav;
    }

    /**
     * 根据Id查询详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/getExpenseEntityById")
    @ResponseBody
    public String getExpenseEntityById(@RequestParam("id") String id) {
        ExpenseEntity entity = expenseService.getExpenseEntityById(id);
        return JSON.toJSONString(entity);
    }

    /**
     * 新建申请
     *
     * @param entity
     * @return
     */
    @RequestMapping("/addExpenseEntity")
    @ResponseBody
    public Object addExpenseEntity(@RequestBody ExpenseEntity entity) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            entity.setState(0);
            entity.setCreateDate(new Date().getTime());
            String businessKey = expenseService.saveExpenseEntity(entity);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("businessKey", businessKey);
            map.put("code", 200);
            map.put("data", dataMap);
            map.put("msg", "保存成功");
            return JSON.toJSONString(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", 200);
            map.put("msg", "保存失败");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 更新申请信息
     *
     * @param entity
     * @return
     */
    @RequestMapping("/updateExpenseEntity")
    @ResponseBody
    public Object updateExpenseEntity(@RequestBody ExpenseEntity entity) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            expenseService.updateExpenseEntity(entity);
            map.put("code", 200);
            map.put("msg", "保存成功");
            return JSON.toJSONString(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", 200);
            map.put("msg", "保存失败");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 更新申请信息
     *
     * @param param
     * @return
     */
    @RequestMapping("/updateProcInstId")
    @ResponseBody
    public Object updateProcInstId(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            expenseService.updateProcInstId(param);
            map.put("code", 200);
            map.put("msg", "保存成功");
            return JSON.toJSONString(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", 200);
            map.put("msg", "保存失败");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 删除申请
     *
     * @param param
     * @return
     */
    @RequestMapping("/deleteExpenseEntity")
    @ResponseBody
    public Object deleteExpenseEntity(@RequestBody Map<String, String> param) {
        String id = param.get("id");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            expenseService.deleteExpenseEntity(id);
            map.put("code", 200);
            map.put("msg", "删除成功");
            return JSON.toJSONString(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", 200);
            map.put("msg", "删除失败");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 更新申请状态
     *
     * @param param
     * @return
     */
    @RequestMapping("/changeExpenseEntityStatus")
    @ResponseBody
    public Object changeExpenseEntityStatus(@RequestBody Map<String, Object> param) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            expenseService.changeExpenseEntityStatus(param);
            map.put("code", 200);
            map.put("msg", "删除成功");
            return JSON.toJSONString(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", 200);
            map.put("msg", "删除失败");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 提交(判断是否有id,如果有id,做更新操作，然后再做提交工作流工作;如果没有id,先做保存，然后再提交工作流)
     *
     * @param entity
     * @return
     */
    @RequestMapping("/submitExpenseEntity")
    @ResponseBody
    public Object submitExpenseEntity(@RequestBody ExpenseEntity entity) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            expenseService.submitExpenseEntity(entity);
            map.put("code", 200);
            map.put("msg", "保存成功");
            return JSON.toJSONString(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("code", 200);
            map.put("msg", "保存失败");
            return JSON.toJSONString(map);
        }
    }


}
