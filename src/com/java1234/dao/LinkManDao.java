package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.LinkMan;

/**
 * 联系人Dao
 */
public interface LinkManDao {

	/**
	 * 查询联系人集合
	 * @param linkMan
	 * @return
	 */
	public List<LinkMan> find(Map<String, Object> linkMan);
	
	/**
	 * 获取总记录数
	 * @param linkMan
	 * @return
	 */
	public Long getTotal(Map<String, Object> linkMan);
	
	/**
	 * 修改
	 * @param linkMan
	 * @return
	 */
	public int update(LinkMan linkMan);
	
	/**
	 * 添加
	 * @param linkMan
	 * @return
	 */
	public int add(LinkMan linkMan);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}
