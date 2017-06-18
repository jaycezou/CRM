package com.java1234.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java1234.dao.OrderDao;
import com.java1234.entity.Order;
import com.java1234.service.OrderService;

/**
 * 历史订单service实现
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public List<Order> find(Map<String, Object> order) {
		return orderDao.find(order);
	}

	@Override
	public Long getTotal(Map<String, Object> order) {
		return orderDao.getTotal(order);
	}

	@Override
	public Order findById(Integer id) {
		return orderDao.findById(id);
	}

}
