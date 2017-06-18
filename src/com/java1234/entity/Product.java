package com.java1234.entity;

/**
 * 产品实体
 */
public class Product {

	private Integer id;
	private String productName;//产品名称
	private String model;//型号
	private String unit;//单位
	private String price;//价格
	private String store;//库存
	private String remark;//备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", model=" + model + ", unit=" + unit + ", price="
				+ price + ", store=" + store + ", remark=" + remark + "]";
	}
}
