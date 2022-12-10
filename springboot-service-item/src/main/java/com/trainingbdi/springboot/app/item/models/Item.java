package com.trainingbdi.springboot.app.item.models;

public class Item {

	private Products products;
	private Integer amount;

	public Item() {
	}

	public Item(Products products, Integer amount) {
		this.products = products;
		this.amount = amount;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Double getTotal() {
		return products.getPrice() * amount.doubleValue();
	}

}
