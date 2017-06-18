package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.OrderDetails;
import com.java1234.entity.PageBean;
import com.java1234.service.OrderDetailsService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 订单明细
 */
@Controller
@RequestMapping("/orderDetails")
public class OrderDetailsController {

	@Resource
	private OrderDetailsService orderDetailsService;
	
	/**
	 * 查询订单
	 * @param orderId
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="orderId", required=false)String orderId,String page, String rows,
			HttpServletResponse response) throws Exception{
		if(StringUtil.isEmpty(orderId))
			return null;
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<OrderDetails> orderDetailsList = orderDetailsService.find(map);
		Long total = orderDetailsService.getTotal(map);
		JSONObject jsonObject = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		//去除order属性，防止死循环？
		jsonConfig.setExcludes(new String[]{"order"});
		JSONArray jsonArray  = JSONArray.fromObject(orderDetailsList, jsonConfig);
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", total);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 根据订单获取总金额
	 * @param orderId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTotalPrice")
	public String getTotalPrice(String orderId,HttpServletResponse response)throws Exception{
		float sum = orderDetailsService.getTotalPriceByOrderId(Integer.parseInt(orderId));
		JSONObject result = new JSONObject();
		result.put("totalMoney", sum);
		ResponseUtil.write(response, result);
		return null;
	}
}
