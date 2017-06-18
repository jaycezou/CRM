package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.DataDic;

/**
 * 数据字典Dao
 */
public interface DataDicDao {

	/**
	 * 查询数据字典集合
	 * @param dataDic
	 * @return
	 */
	public List<DataDic> find(Map<String, Object> dataDic);
	
	/**
	 * 查询所有的数据字典名称
	 * @return
	 */
	public List<DataDic> findAllNames();
	
	/**
	 * 获取总记录数
	 * @param dataDic
	 * @return
	 */
	public Long getTotal(Map<String, Object> dataDic);
	
	/**
	 * 修改
	 * @param dataDic
	 * @return
	 */
	public int update(DataDic dataDic);
	
	/**
	 * 添加
	 * @param dataDic
	 * @return
	 */
	public int add(DataDic dataDic);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}
