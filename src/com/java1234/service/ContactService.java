package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Contact;

/**
 * 交往记录service
 */
public interface ContactService {
	/**
	 * 查询交往记录集合
	 * @param contact
	 * @return
	 */
	public List<Contact> find(Map<String, Object> contact);
	
	/**
	 * 获取总记录数
	 * @param contact
	 * @return
	 */
	public Long getTotal(Map<String, Object> contact);
	
	/**
	 * 修改
	 * @param contact
	 * @return
	 */
	public int update(Contact contact);
	
	/**
	 * 添加
	 * @param contact
	 * @return
	 */
	public int add(Contact contact);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}
