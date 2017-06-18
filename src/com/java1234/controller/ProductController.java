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
import com.java1234.entity.Product;
import com.java1234.service.ProductService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 产品Controller
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	@Resource
	public ProductService productService;
	
	/**
	 * 分页查询产品
	 * @param page
	 * @param rows
	 * @param s_product
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("list")
	public String list(@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,Product s_product,
			HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("productName", StringUtil.formatLike(s_product.getProductName()));
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<Product> productList = productService.find(map);
			Long total = productService.getTotal(map);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray  = JSONArray.fromObject(productList);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", total);
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
}
