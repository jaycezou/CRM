package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.CustomerLoss;

/**
 * 客户流失service
 */
public interface CustomerLossService {

	/**
	 * 查询客户流失集合
	 * @param customerLoss
	 * @return
	 */
	public List<CustomerLoss> find(Map<String, Object> map);
	
	/**
	 * 获取总记录数
	 * @param customerLoss
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * 修改
	 * @param customerLoss
	 * @return
	 */
	public int update(CustomerLoss customerLoss);
	
	/**
	 * 添加
	 * @param customerLoss
	 * @return
	 */
	public int add(CustomerLoss customerLoss);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public CustomerLoss findById(Integer id);
}
