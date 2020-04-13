package com.flowable.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.flowable.springboot.bean.ExpenseDetail;
import com.flowable.springboot.bean.ExpenseEntity;
import com.flowable.springboot.dao.ExpenseDao;
import com.flowable.springboot.service.ExpenseService;
import com.flowable.springboot.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Resource
	ExpenseDao expenseDao;

	@Override
	public List<ExpenseEntity> listExpenseEntityByPage(int pageSize, int pageNum) {
		return expenseDao.listExpenseByPage(pageSize, pageNum);
	}

	@Override
	public ExpenseEntity getExpenseEntityById(String id) {
		List<ExpenseDetail> items = expenseDao.getExpenseDetail(id);
		ExpenseEntity entity = expenseDao.getExpenseEntity(id);
		entity.setItems(items);
		return entity;
	}

	//@LcnTransaction
	@Transactional
	@Override
	public String saveExpenseEntity(ExpenseEntity entity) {
		String Id = UUID.randomUUID().toString();
		entity.setId(Id);
		double sum = 0.0;
		List<ExpenseDetail> items = entity.getItems();
		for(ExpenseDetail detail : items){
			detail.setId(Id);
			sum+=detail.getSum();
			if (StringUtils.isBlank(detail.getType())) {
				detail.setType("");
			}
			expenseDao.saveExpenseDetail(detail);
		}
		entity.setSum(sum);
		expenseDao.saveExpenseEntity(entity);
		return Id;
	}

	//@LcnTransaction
	@Transactional
	@Override
	public void updateExpenseEntity(ExpenseEntity entity) {
		String id = entity.getId();
		List<ExpenseDetail> items = entity.getItems();
		double sum=0.0;
		expenseDao.deleteExpenseDetail(id);
		for(ExpenseDetail detail : items){
			sum+=detail.getSum();
			detail.setId(id);
			expenseDao.saveExpenseDetail(detail);
		}
		entity.setSum(sum);
		expenseDao.updateEntity(entity);
	}

	//@LcnTransaction
	@Transactional
	@Override
	public void deleteExpenseEntity(String id) {
		expenseDao.deleteExpenseEntity(id);
		expenseDao.deleteExpenseDetail(id);
	}

	@Override
	public List<ExpenseEntity> listExpenseEntityByPage(String userCode) {
		List<ExpenseEntity> list = expenseDao.listExpenseByPageV2(userCode);
		return list;
	}

	//@LcnTransaction
	@Transactional
	@Override
	public void changeExpenseEntityStatus(Map<String,Object> param) {
		//业务id
		String id = (String) param.get("id");
		//流程状态
		int processState = (int) param.get("processState");
		//审核意见
		//String comment = (String) param.get("comment");
		//审核人
		String userCode = (String) param.get("userCode");
		
		//审批信息
//		@SuppressWarnings("unchecked")
//		Map<String,Object> approveMap = (Map<String, Object>) param.get("approveData");
//		System.out.println("approveMap: " + approveMap);
//		String comment = (String) approveMap.get("comment");
//		List<Map<String,Object>> nextNodeInfos = (List<Map<String, Object>>) approveMap.get("data");
//		String assignee = "";
//		if(nextNodeInfos !=null && nextNodeInfos.size()!=0){
//			Map<String,Object> nodeInfo = nextNodeInfos.get(0);
//			//获取下一节点的审核人
//			List<String> users = (List<String>) nodeInfo.get("users");
//			if(users!=null && users.size()==1){
//				assignee = users.get(0);
//			}
//		}
		
		ExpenseEntity entity = getExpenseEntityById(id);
		//查询到流程实例Id
		String processInstancId = entity.getProcessId();
		
//		//TODO 业务系统与工作流系统关联查询得到taskId
//		String task_url = "http://10.16.21.29:9000/api/runtime/fbpm-user-task/"+userCode;
//		String res = HttpUtil.doGet(task_url, null);
//		List<Map> list = JSON.parseArray(res, Map.class);
		
		
		
		//String taskId = "c31ed4cf-5546-11e9-bc7a-1002b52dabf9";
		String taskId = (String) param.get("taskId");
		Map<String,Object> taskActionRequest = new HashMap<String,Object>();
		//taskActionRequest.put("assignee", assignee);
		taskActionRequest.put("action", "complete");
		
//		//调用审核服务
//		//TODO
//		         //"http://localhost:8083/fbpm-process/api/runtime/fbpm-approve-tasks";
//		String url="http://localhost:8083/fbpm-process/api/runtime/fbpm-approve-tasks/"+taskId;
//		String resMsg = HttpUtil.sendPost(url, JSON.toJSONString(taskActionRequest));
//		System.out.println("审核： " + resMsg);
		//更新状态
		
		//更改成下一个节点的userCode

		expenseDao.changeExpenseEntityStatus(id,processState);
	}

	/**
	 * 提交
	 */
	//@LcnTransaction
	@Transactional
	@Override
	public void submitExpenseEntity(ExpenseEntity entity) {
		String businessKey="";
		if(StringUtils.isNotEmpty(entity.getId())){
			//updateExpenseEntity(entity);
			businessKey = entity.getId();
		}else{
			businessKey = saveExpenseEntity(entity);
		}
		//提交工作流
		String url="http://localhost:8889/fbpm-process/api/runtime/fbpm-process-instances";
		Map<String,Object> processInstanceCreateRequest = new HashMap<String,Object>();
		processInstanceCreateRequest.put("userId", entity.getUserCode());
		processInstanceCreateRequest.put("businessKey", businessKey);
		String procDefId = System.getProperty("test.procDefId");
		//aaa-bbb-ttt:1:23b360b4-649a-11e9-9f4d-1002b52dabf9  aaa:4:f012f270-6275-11e9-a977-1002b52dabf9
		//aa-bb-dd-ff:1:9f09f54b-600a-11e9-88d7-fa163e1fd5b7  aaa-bbb-ccc:1:2fea7030-5518-11e9-991e-fa163e1fd5b7
		processInstanceCreateRequest.put("processDefinitionId", procDefId);
		
	    String msg = HttpUtil.sendPost(url, JSON.toJSONString(processInstanceCreateRequest));
	    Map<String,String> resMap = JSON.parseObject(msg, Map.class);
	    String processInstanceId = resMap.get("id");
	    
	    //更新状态
		expenseDao.updateExpenseEntity(businessKey,2,processInstanceId);
	    
	    System.out.println("msg: "+msg);
	}

	@Override
	public List<ExpenseEntity> listExpenseEntityByPageTask(Map<String, Object> param) {
		List<ExpenseEntity> list = expenseDao.listExpenseByPageV3(param);
		return list;
	}

	@Override
	public void updateProcInstId(Map<String, Object> param) {
		String businessKey = (String) param.get("businessKey");
		String procInstId = (String) param.get("procInstId");
		expenseDao.updateProcInstId(param);
	}

    /**
     * 修改单据状态
     *
     * @param businessKey
     * @param state
     */
    @Override
    public void updateState(String businessKey, String state) {
		expenseDao.updateState(businessKey,state);
    }

    /**
     * 未提交列表查询
     *
	 * @param userCode
     * @param ids
     * @return
     */
    @Override
    public List<ExpenseEntity> queryExpenses(String userCode,String[] ids) {
        return expenseDao.queryExpenses(userCode,ids);
    }

	/**
	 * 已提交、未审批、已审批列表查询
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public List<ExpenseEntity> queryExpenses2(String[] ids) {
		return expenseDao.queryExpenses2(ids);
	}
}
