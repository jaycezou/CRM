package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.SaleChance;

/**
 * 销售机会service
 */
public interface SaleChanceService {

	/**
	 * 查询销售机会集合
	 * @param saleChance
	 * @return
	 */
	public List<SaleChance> find(Map<String, Object> saleChance);
	/**
	 * 查找实体
	 * @param id 实体ID
	 * @return
	 */
	public SaleChance findById(Integer id);
	/**
	 * 获取总记录数
	 * @param saleChance
	 * @return
	 */
	public Long getTotal(Map<String, Object> saleChance);
	
	/**
	 * 添加
	 * @param saleChance
	 * @return
	 */
	public int add(SaleChance saleChance);
	/**
	 * 更新
	 * @param saleChance
	 * @return
	 */
	public int update(SaleChance saleChance);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}
