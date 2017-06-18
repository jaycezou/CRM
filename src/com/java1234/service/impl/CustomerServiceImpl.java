package com.java1234.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java1234.dao.CustomerDao;
import com.java1234.dao.CustomerLossDao;
import com.java1234.dao.OrderDao;
import com.java1234.entity.Customer;
import com.java1234.entity.CustomerFw;
import com.java1234.entity.CustomerGc;
import com.java1234.entity.CustomerGx;
import com.java1234.entity.CustomerLoss;
import com.java1234.entity.Order;
import com.java1234.service.CustomerService;

/**
 * 客户service实现
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Resource
	private CustomerLossDao customerLossDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Override
	public List<Customer> find(Map<String, Object> customer) {
		return customerDao.find(customer);
	}

	@Override
	public Long getTotal(Map<String, Object> customer) {
		return customerDao.getTotal(customer);
	}

	@Override
	public int add(Customer customer) {
		return customerDao.add(customer);
	}

	@Override
	public int update(Customer customer) {
		return customerDao.update(customer);
	}

	@Override
	public int delete(Integer id) {
		return customerDao.delete(id);
	}

	@Override
	public Customer findById(Integer id) {
		return customerDao.findById(id);
	}
	
	@Override
	public void checkLossCustomer(){
		List<Customer> customerList = customerDao.findLossCustomers();
		for(Customer customer:customerList){
			CustomerLoss customerLoss = new CustomerLoss();
			customerLoss.setCusNo(customer.getKhno());//客户编号
			customerLoss.setCusName(customer.getName());//客户名称
			customerLoss.setCusManager(customer.getCusManager());//客户经理
			Order order = orderDao.findLastOrderByCusId(customer.getId());
			if(order!=null){
				customerLoss.setLastOrderTime(order.getOrderDate());//最后订单日期
				customerLoss.setLossReason("6个月未下单");
			}else{
				customerLoss.setLastOrderTime(null);//最后订单日期
				customerLoss.setLossReason("注册后6个月都未下单");
			}
			customerLoss.setConfirmLossTime(null);//确认流失日期
			customerLoss.setState(0);//默认暂缓流失
			customerLossDao.add(customerLoss);
			customer.setState(1);//设置客户流失状态
			customerDao.update(customer);
		}
	}

	@Override
	public List<CustomerGx> findCustomerGx(Map<String, Object> map) {
		return customerDao.findCustomerGx(map);
	}

	@Override
	public Long getTotalCustomerGx(Map<String, Object> map) {
		return customerDao.getTotalCustomerGx(map);
	}

	@Override
	public List<CustomerGc> findCustomerGc() {
		return customerDao.findCustomerGc();
	}

	@Override
	public List<CustomerFw> findCustomerFw() {
		return customerDao.findCustomerFw();
	}
}
