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

import com.java1234.entity.CustomerLoss;
import com.java1234.entity.PageBean;
import com.java1234.service.CustomerLossService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 客户流失Controller
 */
@Controller
@RequestMapping("/customerLoss")
public class CustomerLossController {

	@Resource
	public CustomerLossService customerLossService;
	
	/**
	 * 分页查询客户流失
	 * @param page
	 * @param rows
	 * @param customerName
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(CustomerLoss customerLoss, int page, int rows,
		HttpServletResponse response) throws Exception{
		PageBean pageBean = new PageBean(page, rows);
		Map<String, Object> map = new HashMap<>();
		map.put("cusManager", StringUtil.formatLike(customerLoss.getCusManager()));
		map.put("cusName", StringUtil.formatLike(customerLoss.getCusName()));
		map.put("state", customerLoss.getState());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CustomerLoss> customerLossList = customerLossService.find(map);
		JSONObject jsonObject = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray  = JSONArray.fromObject(customerLossList, jsonConfig);
		jsonObject.put("rows", jsonArray);
		jsonObject.put("total", customerLossService.getTotal(map));
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(CustomerLoss customerLoss, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(customerLoss.getId()==null || customerLoss.getId().toString()==""){
			resultTotal = customerLossService.add(customerLoss);
		}else{
			resultTotal = customerLossService.update(customerLoss);
		}
		JSONObject jsonObject = new JSONObject();
		if(resultTotal>0){
			jsonObject.put("success", true);
		}else{
			jsonObject.put("success", false);
		}
		ResponseUtil.write(response, jsonObject);
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
	public String delete(@RequestParam(value="id")String id, HttpServletResponse response) throws Exception{
		customerLossService.delete(Integer.parseInt(id));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 通过id查找
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/findById")
	public String findById(String id, HttpServletResponse response) throws Exception{
		CustomerLoss customerLoss = customerLossService.findById(Integer.parseInt(id));
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject jsonObject = JSONObject.fromObject(customerLoss, jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 确认客户流失
	 * @param id
	 * @param lossReason
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("confirmLoss")
	public String confirmLoss(int id, String lossReason, HttpServletResponse response) throws Exception{
		CustomerLoss customerLoss = new CustomerLoss();
		customerLoss.setId(id);
		customerLoss.setLossReason(lossReason);
		customerLoss.setConfirmLossTime(new Date());
		customerLoss.setState(1);//确认流失
		customerLossService.update(customerLoss);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	
}
