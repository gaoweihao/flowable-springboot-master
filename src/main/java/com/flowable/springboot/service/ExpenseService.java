package com.flowable.springboot.service;

import com.flowable.springboot.bean.ExpenseEntity;

import java.util.List;
import java.util.Map;


public interface ExpenseService {
	
	List<ExpenseEntity> listExpenseEntityByPage(int pageSize, int pageNum);

	/**
	 * 根据id查询报销信息
	 * @param id
	 * @return
	 */
	ExpenseEntity getExpenseEntityById(String id);

	/**
	 * 保存
	 * @param entity
	 */
	String saveExpenseEntity(ExpenseEntity entity);

	/**
	 * 编辑保存
	 * @param entity
	 */
	void updateExpenseEntity(ExpenseEntity entity);

	/**
	 * 删除
	 * @param id
	 */
	void deleteExpenseEntity(String id);

	/**
	 * 分页查询
	 * @param userCode
	 * @return
	 */
	List<ExpenseEntity> listExpenseEntityByPage(String userCode);

	/**
	 * 更新状态
	 * @param param
	 */
	void changeExpenseEntityStatus(Map<String, Object> param);

	/**
	 * 提交操作
	 * @param entity
	 */
	void submitExpenseEntity(ExpenseEntity entity);

	/**
	 * 代办列表
	 * @param param
	 * @return
	 */
	List<ExpenseEntity> listExpenseEntityByPageTask(Map<String, Object> param);

	/**
	 * 更新流程实例Id
	 * @param param
	 */
	void updateProcInstId(Map<String, Object> param);

	/**
	 * 修改单据状态
	 *
	 * @param businessKey
	 * @param state
	 */
	void updateState(String businessKey, String state);

	/**
	 * 未提交列表查询
	 *
	 * @param userCode
	 * @param ids
	 * @return
	 */
	List<ExpenseEntity> queryExpenses(String userCode, String[] ids);

	/**
	 * 已提交、未审批、已审批列表查询
	 *
	 * @param ids
	 * @return
	 */
	List<ExpenseEntity> queryExpenses2(String[] ids);
}
