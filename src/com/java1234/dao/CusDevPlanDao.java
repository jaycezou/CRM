package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.CusDevPlan;

/**
 * 客户开发计划Dao
 */
public interface CusDevPlanDao {

	/**
	 * 查询客户开发计划集合
	 * @param cusDevPlan
	 * @return
	 */
	public List<CusDevPlan> find(Map<String, Object> cusDevPlan);
	
	/**
	 * 获取总记录数
	 * @param cusDevPlan
	 * @return
	 */
	public Long getTotal(Map<String, Object> cusDevPlan);
	
	/**
	 * 修改
	 * @param cusDevPlan
	 * @return
	 */
	public int update(CusDevPlan cusDevPlan);
	
	/**
	 * 添加
	 * @param cusDevPlan
	 * @return
	 */
	public int add(CusDevPlan cusDevPlan);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}
