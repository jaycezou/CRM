package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.CustomerService;

/**
 * 客户服务Dao接口
 * @author Administrator
 *
 */
public interface CustomerServiceDao {

	/**
	 * 添加客户服务
	 * @param customerService
	 * @return
	 */
	public int add(CustomerService customerService);
	
	public List<CustomerService> find(Map<String, Object> map);
	
	public Long getTotal(Map<String, Object> map);
	
	public int update(CustomerService customerService);
}
