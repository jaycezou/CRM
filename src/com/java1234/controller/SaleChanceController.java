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

import com.java1234.entity.PageBean;
import com.java1234.entity.SaleChance;
import com.java1234.service.SaleChanceService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 销售机会Controller
 */
@Controller
@RequestMapping("/saleChance")
public class SaleChanceController {

	@Resource
	public SaleChanceService saleChanceService;
	
	/**
	 * SpringMVC 把日期字符串转为日期对象
	 * @param binder
	 */
	 @InitBinder
	 public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 分页查询销售机会
	 * @param page
	 * @param rows
	 * @param customerName
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,SaleChance s_saleChance,
			HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", StringUtil.formatLike(s_saleChance.getCustomerName()));
			map.put("overView", StringUtil.formatLike(s_saleChance.getOverView()));
			map.put("createMan", StringUtil.formatLike(s_saleChance.getCreateMan()));
			map.put("state", s_saleChance.getState());
			map.put("devResult", s_saleChance.getDevResult());
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<SaleChance> saleChanceList = saleChanceService.find(map);
			Long total = saleChanceService.getTotal(map);
			JSONObject jsonObject = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			//日期过滤
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
			JSONArray jsonArray  = JSONArray.fromObject(saleChanceList, jsonConfig);
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
		SaleChance saleChance = saleChanceService.findById(Integer.parseInt(id));
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONObject result = JSONObject.fromObject(saleChance, jsonConfig);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(SaleChance saleChance, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(StringUtil.isNotEmpty(saleChance.getAssignMan())){
			saleChance.setState(1);
		}else{
			saleChance.setState(0);//默认未分配
		}
		if(saleChance.getId()==null || saleChance.getId().toString()==""){
			saleChance.setId(null);
			saleChance.setDevResult(0);
			resultTotal = saleChanceService.add(saleChance);
		}else{
			resultTotal = saleChanceService.update(saleChance);
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
	public String delete(@RequestParam(value="ids")String ids, HttpServletResponse response) throws Exception{
		String[] idsArr = ids.split(",");
		for(int i=0; i<idsArr.length; i++){
			saleChanceService.delete(Integer.parseInt(idsArr[i]));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
}
