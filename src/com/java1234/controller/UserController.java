package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java1234.entity.PageBean;
import com.java1234.entity.User;
import com.java1234.service.UserService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	public UserService userService;
	
	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request) throws Exception{
		User resultUser = userService.login(user);
		if(resultUser==null){
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误！");
			return "login";
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", resultUser);
			return "redirect:/main.jsp";
		}
	}
	
	/**
	 * 分页查询用户
	 * @param page
	 * @param rows
	 * @param s_user
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("list")
	public String list(@RequestParam(value="page", required=false)String page,
			@RequestParam(value="rows", required=false)String rows,User s_user,
			HttpServletResponse response) throws Exception{
			PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
			Map<String, Object> map = new HashMap<>();
			map.put("userName", StringUtil.formatLike(s_user.getUserName()));
			map.put("start", pageBean.getStart());
			map.put("size", pageBean.getPageSize());
			List<User> userList = userService.find(map);
			Long total = userService.getTotal(map);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray  = JSONArray.fromObject(userList);
			jsonObject.put("rows", jsonArray);
			jsonObject.put("total", total);
			ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 真实姓名combobox
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("customerManagerComboboxList")
	public String customerManagerComboboxList(HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("roleName", "客户经理");
		List<User> userList = userService.find(map);
		JSONArray jsonArray = JSONArray.fromObject(userList);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	
	/**
	 * 保存或更新用户信息
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping("/save")
	public String save(User user, HttpServletResponse response) throws Exception{
		int resultTotal = 0;
		if(user.getId()==null || user.getId().toString()==""){
			user.setId(null);
			resultTotal = userService.add(user);
		}else{
			resultTotal = userService.update(user);
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
	 * 删除用户
	 * @param ids 以英文逗号隔开的id字符串
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids, HttpServletResponse response) throws Exception{
		String[] idsStr  = ids.split(",");
		for(String id : idsStr){
			userService.delete(Integer.parseInt(id));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 修改用户密码
	 * @return
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(User user,HttpServletResponse response) throws Exception{
		int resultTotal = userService.update(user);
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
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception{
		session.invalidate();//清空session
		return "redirect:/login.jsp";
	}
}
