package com.flowable.springboot.dao;
import com.flowable.springboot.bean.ExpenseDetail;
import com.flowable.springboot.bean.ExpenseEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


public interface ExpenseDao {
	
	@Select("select id,type_,dept_Name,reason,sum_,state, status,user_name,date_ "
		  + "from expenses")
	List<ExpenseEntity> listExpenseByPage(@Param("pageSize") int pageSize, @Param("pageNum") int pageNum);

	/**
	 * 根据id获取报销详情
	 * @param id
	 * @return
	 */
	@Select("select *  from expenses_detail where id=#{id}")
	List<ExpenseDetail> getExpenseDetail(@Param("id") String id);

	/**
	 * 根据id查询报销基本信息
	 * @param id
	 * @return
	 */
	@Select("select id,type_,dept_Name,reason,sum_,state,process_State,status,user_name, date_,user_Code,process_Id "
		  + " from expenses where id=#{id}")
	ExpenseEntity getExpenseEntity(@Param("id") String id);

	/**
	 * 保存报销基本信息
	 * @param entity
	 */
	void saveExpenseEntity(ExpenseEntity entity);

	/**
	 * 保存详情
	 * @param detail
	 */
	void saveExpenseDetail(ExpenseDetail detail);

	/**
	 * 删除
	 * @param id
	 */
	@Delete("delete from expenses_detail where id=#{id}")
	void deleteExpenseDetail(@Param("id") String id);

	/**
	 * 更新
	 * @param entity
	 */
	@Update("update expenses set sum_=${sum},reason=#{reason},type_=#{type},dept_Name=#{deptName},date_=#{date} where id=#{id}")
	void updateEntity(ExpenseEntity entity);

	/**
	 * 删除
	 * @param id
	 */
	@Delete("delete from expenses where id=#{id}")
	void deleteExpenseEntity(@Param("id") String id);

	List<ExpenseEntity> listExpenseByPageV2(@Param("userCode") String userCode);

	/**
	 * 更新状态
	 * @param id
	 */
	@Update(" update expenses set process_State=${process_State} where id=#{id}")
	void changeExpenseEntityStatus(@Param("id") String id, @Param("processState") int processState);

	/**
	 * 
	 * @param businessKey
	 * @param processInstanceId
	 */
	@Update("update expenses set process_State=${processState},process_Id=#{processId} where id=#{businessKey}")
	void updateExpenseEntity(@Param("businessKey") String businessKey, @Param("processState") int processState, @Param("processId") String processInstanceId);

	@SelectProvider(type = ExpenseByPage.class, method = "getTaskList")
	List<ExpenseEntity> listExpenseByPageV3(Map<String, Object> param);

	/**
	 * 更新流程实例Id
	 * @param param
	 */
	@Update("update expenses set process_Id=#{procInstId} where id=#{businessKey}")
	void updateProcInstId(Map<String, Object> param);

    /**
     * 修改单据状态
     *
     * @param businessKey
     * @param state
     */
	void updateState(@Param("businessKey") String businessKey, @Param("state") String state);

	/**
	 * 未提交列表查询
	 *
	 * @param userCode
	 * @param ids
	 * @return
	 */
	List<ExpenseEntity> queryExpenses(@Param("userCode") String userCode, @Param("ids") String[] ids);

	/**
	 * 已提交、未审批、已审批列表查询
	 *
	 * @param ids
	 * @return
	 */
	List<ExpenseEntity> queryExpenses2(@Param("ids") String[] ids);

}
