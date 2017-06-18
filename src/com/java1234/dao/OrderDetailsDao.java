package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.OrderDetails;

/**
 * 订单明细Dao
 */
public interface OrderDetailsDao {

	/**
	 * 查询订单明细集合
	 * @param order
	 * @return
	 */
	public List<OrderDetails> find(Map<String, Object> map);
	
	/**
	 * 获取总记录数
	 * @param order
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * 获取指定订单的总金额
	 * @param orderId
	 * @return
	 */
	public float getTotalPriceByOrderId(Integer orderId);
	
}
