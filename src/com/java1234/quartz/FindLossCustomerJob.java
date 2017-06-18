package com.java1234.quartz;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.java1234.service.CustomerService;

/**
 * 查找流失客户定时任务
 * @author 27063
 *
 */
@Component
public class FindLossCustomerJob {

	@Resource
	private CustomerService customerService;
	
	/**
	 * 定时任务内容 
	 * 凌晨两点：0 0 2 * * ?
	 * 每分钟一次：0 0/1 * * * ?
	 */
	@Scheduled(cron="0 0/1 * * * ?")
	public void work(){
		customerService.checkLossCustomer();
	}
}
