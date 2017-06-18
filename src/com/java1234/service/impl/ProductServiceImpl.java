package com.java1234.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java1234.dao.ProductDao;
import com.java1234.entity.Product;
import com.java1234.service.ProductService;

/**
 * 产品service实现类
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductDao productDao;
	
	@Override
	public List<Product> find(Map<String, Object> product) {
		return productDao.find(product);
	}

	@Override
	public Long getTotal(Map<String, Object> product) {
		return productDao.getTotal(product);
	}

}
