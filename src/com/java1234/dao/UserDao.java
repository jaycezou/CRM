package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.User;

/**
 * 用户Dao
 */
public interface UserDao {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);
	
	/**
	 * 查询用户集合
	 * @param user
	 * @return
	 */
	public List<User> find(Map<String, Object> user);
	
	/**
	 * 获取总记录数
	 * @param user
	 * @return
	 */
	public Long getTotal(Map<String, Object> user);
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	public int update(User user);
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	public int add(User user);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}
