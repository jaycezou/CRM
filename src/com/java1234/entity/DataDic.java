package com.java1234.entity;

/**
 * 数据字典实体
 */
public class DataDic {

	private Integer id;
	private String dataDicName;//数据字典名称
	private String dataDicValue;//数据字典值
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDataDicName() {
		return dataDicName;
	}
	public void setDataDicName(String dataDicName) {
		this.dataDicName = dataDicName;
	}
	public String getDataDicValue() {
		return dataDicValue;
	}
	public void setDataDicValue(String dataDicValue) {
		this.dataDicValue = dataDicValue;
	}
}
