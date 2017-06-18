package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.PageBean;
import com.java1234.entity.DataDic;
import com.java1234.service.DataDicService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据字典Controller
 */
@Controller
@RequestMapping("/dataDic")
public class DataDicController {

	@Resource
	public DataDicService dataDicService;
	
	/**
	 * 分页查询数据字典
	 * @param page
	 * @param rows
	 * @param s_dataDic
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("list")
	public String list(@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,DataDic s_dataDic,
			HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("dataDicName", s_dataDic.getDataDicName());
			map.put("dataDicValue", StringUtil.formatLike(s_dataDic.getDataDicValue()));
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<DataDic> dataDicList = dataDicService.find(map);
			Long total = dataDicService.getTotal(map);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray  = JSONArray.fromObject(dataDicList);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", total);
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 根据数据字典名称查找其所有值
	 * @param dataDicName
	 * @param response
	 * @return
	 */
	@RequestMapping("findDataDicValues")
	public String findAllDadaDicValues(String dataDicName, HttpServletResponse response) throws Exception{
		Map<String, Object> dataDic = new HashMap<String, Object>();
		dataDic.put("dataDicName", dataDicName);
		List<DataDic> dataDicList = dataDicService.find(dataDic);
		JSONArray result = JSONArray.fromObject(dataDicList);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 数据字典名称combobox
	 * @return
	 */
	@RequestMapping("/findDataDicName")
	public String findAllDadaDicNames(HttpServletResponse response) throws Exception{
		List<DataDic> dataDicList = dataDicService.findAllNames();
		DataDic dataDic = new DataDic();
		dataDic.setDataDicName("请选择···");
		dataDicList.add(0, dataDic);
		JSONArray jsonArray = JSONArray.fromObject(dataDicList);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	
	/**
	 * 保存或更新数据字典信息
	 * @param dataDic
	 * @param response
	 * @return
	 */
	@RequestMapping("/save")
	public String save(DataDic dataDic, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(dataDic.getId()==null || dataDic.getId().toString()==""){
			dataDic.setId(null);
			resultTotal = dataDicService.add(dataDic);
		}else{
			resultTotal = dataDicService.update(dataDic);
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
	 * 删除数据字典
	 * @param ids 以英文逗号隔开的id字符串
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	public String delete(@RequestParam(value="ids")String ids, HttpServletResponse response) throws Exception{
		String[] idsStr  = ids.split(",");
		for(String id : idsStr){
			dataDicService.delete(Integer.parseInt(id));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
}
