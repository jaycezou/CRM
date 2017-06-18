package com.java1234.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.CusDevPlan;
import com.java1234.entity.SaleChance;
import com.java1234.service.CusDevPlanService;
import com.java1234.service.SaleChanceService;
import com.java1234.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 客户开发计划Controller
 */
@Controller
@RequestMapping("/cusDevPlan")
public class CusDevPlanController {

	@Resource
	public CusDevPlanService cusDevPlanService;
	@Resource
	public SaleChanceService saleChanceService;
	
	/**
	 * SpringMVC 把日期字符串转为日期对象
	 * @param binder
	 */
	 @InitBinder
	 public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 分页查询客户开发计划
	 * @param page
	 * @param rows
	 * @param customerName
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="saleChanceId", required=false)String saleChanceId,
			HttpServletResponse response) throws Exception{
			Map<String, Object> map = new HashMap<>();
			map.put("saleChanceId", saleChanceId);
			List<CusDevPlan> cusDevPlanList = cusDevPlanService.find(map);
			JSONObject jsonObject = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			//日期过滤
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			//去除saleChance属性，防止死循环？
			jsonConfig.setExcludes(new String[]{"saleChance"});
			JSONArray jsonArray  = JSONArray.fromObject(cusDevPlanList, jsonConfig);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", cusDevPlanList.size());
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(CusDevPlan cusDevPlan, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(cusDevPlan.getId()==null || cusDevPlan.getId().toString()==""){
			SaleChance saleChance = cusDevPlan.getSaleChance();
			saleChance.setDevResult(1);//状态修改成开发中
			saleChanceService.update(saleChance);
			resultTotal = cusDevPlanService.add(cusDevPlan);
		}else{
			resultTotal = cusDevPlanService.update(cusDevPlan);
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
		cusDevPlanService.delete(Integer.parseInt(id));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	/**
	 * @RequestParam("id")String id,@RequestParam("devResult")String devResult
	 * @param saleChance
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateSaleChanceDevResult")
	public String updateSaleChance(SaleChance saleChance,
			HttpServletResponse response) throws Exception{
		int resultTotal = saleChanceService.update(saleChance);
		JSONObject result = new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
}
