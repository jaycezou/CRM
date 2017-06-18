package com.java1234.controller;

import java.text.SimpleDateFormat;
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

import com.java1234.entity.Contact;
import com.java1234.service.ContactService;
import com.java1234.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 交往记录Controller
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

	@Resource
	public ContactService contactService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(sdf, true));
	}
	
	/**
	 * 分页查询交往记录
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
			List<Contact> contactList = contactService.find(map);
			JSONObject jsonObject = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			//去除customer属性，防止死循环？
			jsonConfig.setExcludes(new String[]{"customer"});
			JSONArray jsonArray  = JSONArray.fromObject(contactList, jsonConfig);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", contactList.size());
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(Contact contact, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(contact.getId()==null || contact.getId().toString()==""){
			resultTotal = contactService.add(contact);
		}else{
			resultTotal = contactService.update(contact);
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
		contactService.delete(Integer.parseInt(id));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
}
