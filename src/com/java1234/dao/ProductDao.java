package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Product;

/**
 * 产品Dao
 */
public interface ProductDao {

	/**
	 * 查询产品集合
	 * @param product
	 * @return
	 */
	public List<Product> find(Map<String, Object> product);
	
	/**
	 * 获取总记录数
	 * @param product
	 * @return
	 */
	public Long getTotal(Map<String, Object> product);
	
	/**
	 * 修改
	 * @param product
	 * @return
	 */
	public int update(Product product);
	
}
