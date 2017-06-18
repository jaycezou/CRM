package com.java1234.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.Order;
import com.java1234.entity.PageBean;
import com.java1234.service.OrderService;
import com.java1234.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 历史订单Controller
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	public OrderService orderService;
	
	/**
	 * 分页查询历史订单
	 * @param page
	 * @param rows
	 * @param customerName
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page", required=false)String page,
				@RequestParam(value="rows", required=false)String rows,
				@RequestParam(value="cusId", required=false)String cusId,
				HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("cusId", cusId);
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<Order> orderList = orderService.find(map);
			Long total = orderService.getTotal(map);
			JSONObject jsonObject = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			//去除customer属性，防止死循环？
			jsonConfig.setExcludes(new String[]{"customer"});
			JSONArray jsonArray  = JSONArray.fromObject(orderList, jsonConfig);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", total);
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 根据id查找
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(String id, HttpServletResponse response)throws Exception{
		Order order = orderService.findById(Integer.parseInt(id));
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"order"});
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject jsonObject = JSONObject.fromObject(order, jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
}
