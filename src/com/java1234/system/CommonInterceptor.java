package com.java1234.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.java1234.util.ConstUtil;

public class CommonInterceptor implements HandlerInterceptor {

	/**
	 * 全部完成后处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 拦截后处理
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 拦截前处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		Object obj = request.getSession().getAttribute(ConstUtil.LOGIN_SESSION);
		if(obj!=null){
			return true;
		}else{
			response.sendRedirect("/CRM/login.jsp");
			return false;
		}
	}

}
