package com.java1234.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 返回yyyy-MM-dd类型字符串
	 * @param date java.util.Date
	 * @return
	 */
	public static String formatDate(Date date){
		return formatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 根据格式format格式化date
	 * @param date java.util.Date
	 * @param format java.lang.String
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 根据format返回字符串str对应的日期类型
	 * @param str java.lang.String
	 * @param format java.lang.String
	 * @return java.util.Date
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	/**
	 * 返回yyyyMMddhhmmss字符串
	 * @return java.lang.String
	 * @throws Exception
	 */
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
}
