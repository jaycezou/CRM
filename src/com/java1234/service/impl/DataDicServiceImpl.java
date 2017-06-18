package com.java1234.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java1234.dao.DataDicDao;
import com.java1234.entity.DataDic;
import com.java1234.service.DataDicService;

/**
 * 数据字典service实现
 */
@Service("dataDicService")
public class DataDicServiceImpl implements DataDicService {

	@Resource
	private DataDicDao dataDicDao;
	
	@Override
	public List<DataDic> find(Map<String, Object> dataDic) {
		return dataDicDao.find(dataDic);
	}

	@Override
	public List<DataDic> findAllNames() {
		return dataDicDao.findAllNames();
	}

	@Override
	public Long getTotal(Map<String, Object> dataDic) {
		return dataDicDao.getTotal(dataDic);
	}

	@Override
	public int update(DataDic dataDic) {
		return dataDicDao.update(dataDic);
	}

	@Override
	public int add(DataDic dataDic) {
		return dataDicDao.add(dataDic);
	}

	@Override
	public int delete(Integer id) {
		return dataDicDao.delete(id);
	}

}
