package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.Customer;
import com.java1234.entity.CustomerFw;
import com.java1234.entity.CustomerGc;
import com.java1234.entity.CustomerGx;
import com.java1234.entity.PageBean;
import com.java1234.service.CustomerService;
import com.java1234.util.DateUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 客户信息Controller
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Resource
	public CustomerService customerService;
	
	/**
	 * 分页查询客户信息
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,Customer s_customer,
			HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("khno", StringUtil.formatLike(s_customer.getKhno()));
			map.put("name", StringUtil.formatLike(s_customer.getName()));
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<Customer> customerList = customerService.find(map);
			Long total = customerService.getTotal(map);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray  = JSONArray.fromObject(customerList);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", total);
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	

	/**
	 * 根据ID查找客户开发计划
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id, HttpServletResponse response) throws Exception{
		Customer customer = customerService.findById(Integer.parseInt(id));
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
//		JSONObject result = JSONObject.fromObject(customer, jsonConfig);
		JSONObject result = JSONObject.fromObject(customer);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加/更新
	 * @param customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Customer customer, HttpServletResponse response) throws Exception{
		int count = 0;
		if(customer.getId()==null || customer.getId().toString()==""){
			customer.setKhno("KH"+DateUtil.getCurrentDateStr());//动态生成客户编号
			customer.setState(0);
			count = customerService.add(customer);
		}else{
			count = customerService.update(customer);
		}
		JSONObject result = new JSONObject();
		if(count>0)
			result.put("success", true);
		else
			result.put("success", false);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids, HttpServletResponse response) throws Exception{
		String[] idsStr = ids.split(",");
		for(int i=0; i<idsStr.length; i++){
			customerService.delete(Integer.parseInt(idsStr[i]));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 分页查询客户信息
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/findCustomerGx")
	public String listCustomerGx(@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,String name,
			HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("name", StringUtil.formatLike(name));
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<CustomerGx> customerList = customerService.findCustomerGx(map);
			Long total = customerService.getTotalCustomerGx(map);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray  = JSONArray.fromObject(customerList);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", total);
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 查询用户构成
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCustomerGc")
	public String listCustomerGc(HttpServletResponse response)throws Exception{
		List<CustomerGc> list = customerService.findCustomerGc();
		JSONArray jsonArray  = JSONArray.fromObject(list);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	/**
	 * 查询用户服务
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCustomerFw")
	public String listCustomerFw(HttpServletResponse response)throws Exception{
		List<CustomerFw> list = customerService.findCustomerFw();
		JSONArray jsonArray  = JSONArray.fromObject(list);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
}
