package com.trainingbdi.springboot.app.item.models.service;

import java.util.List;

import com.trainingbdi.springboot.app.item.models.Item;

public interface ItemService {
	
	public List<Item> findAll();
	public Item findBy(Long id, Integer amount);
}
