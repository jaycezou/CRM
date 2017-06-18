package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Customer;
import com.java1234.entity.CustomerFw;
import com.java1234.entity.CustomerGc;
import com.java1234.entity.CustomerGx;

/**
 * 客户信息Service
 */
public interface CustomerService {

	/**
	 * 查询客户信息集合
	 * @param customer
	 * @return
	 */
	public List<Customer> find(Map<String, Object> customer);
	
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public Customer findById(Integer id);
	
	/**
	 * 获取总记录数
	 * @param customer
	 * @return
	 */
	public Long getTotal(Map<String, Object> customer);
	
	/**
	 * 添加
	 * @param customer
	 * @return
	 */
	public int add(Customer customer);
	
	/**
	 * 修改
	 * @param customer
	 * @return
	 */
	public int update(Customer customer);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
	/**
	 * 检查6个月未下单的客户并插入流失表中
	 */
	public void checkLossCustomer();
	/**
	 * 查询客户贡献
	 * @param map
	 * @return
	 */
	public List<CustomerGx> findCustomerGx(Map<String, Object> map);
	
	/**
	 * 查询客户贡献记录数
	 * @param map
	 * @return
	 */
	public Long getTotalCustomerGx(Map<String, Object> map);
	
	/**
	 * 查询客户构成
	 * @param map
	 * @return
	 */
	public List<CustomerGc> findCustomerGc();
	/**
	 * 查询客户服务
	 * @param map
	 * @return
	 */
	public List<CustomerFw> findCustomerFw();
}
