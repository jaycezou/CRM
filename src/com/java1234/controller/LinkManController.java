package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.LinkMan;
import com.java1234.service.LinkManService;
import com.java1234.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 联系人Controller
 */
@Controller
@RequestMapping("/linkMan")
public class LinkManController {

	@Resource
	public LinkManService linkManService;
	
	/**
	 * 分页查询联系人
	 * @param page
	 * @param rows
	 * @param customerName
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="cusId", required=false)String cusId,
			HttpServletResponse response) throws Exception{
			Map<String, Object> map = new HashMap<>();
			map.put("cusId", cusId);
			List<LinkMan> linkManList = linkManService.find(map);
			JSONObject jsonObject = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			//去除customer属性，防止死循环？
			jsonConfig.setExcludes(new String[]{"customer"});
			JSONArray jsonArray  = JSONArray.fromObject(linkManList, jsonConfig);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", linkManList.size());
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(LinkMan linkMan, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(linkMan.getId()==null || linkMan.getId().toString()==""){
			resultTotal = linkManService.add(linkMan);
		}else{
			resultTotal = linkManService.update(linkMan);
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
		linkManService.delete(Integer.parseInt(id));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
}
