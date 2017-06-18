package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Product;

/**
 * 产品service
 * @author 27063
 *
 */
public interface ProductService {

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
}
