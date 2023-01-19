package com.trainingbdi.springboot.app.item.models.service;

import java.util.List;

import com.trainingbdi.springboot.app.item.models.Item;
import com.trainingbdi.springboot.app.item.models.Products;

public interface ItemService {
	
	public List<Item> findAll();
	public Item findBy(Long id, Integer amount);
	
	public Products save(Products products);
	public Products update(Products products, Long id);
	public void delete(Long id);
}