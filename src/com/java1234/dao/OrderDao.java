package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Order;

/**
 * 历史订单Dao
 */
public interface OrderDao {

	/**
	 * 查询历史订单集合
	 * @param order
	 * @return
	 */
	public List<Order> find(Map<String, Object> order);
	
	/**
	 * 获取总记录数
	 * @param order
	 * @return
	 */
	public Long getTotal(Map<String, Object> order);
	
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public Order findById(Integer id);
	
	/**
	 * 查找指定客户最后一个订单
	 * @param cusId
	 * @return
	 */
	public Order findLastOrderByCusId(Integer cusId);
	
}
