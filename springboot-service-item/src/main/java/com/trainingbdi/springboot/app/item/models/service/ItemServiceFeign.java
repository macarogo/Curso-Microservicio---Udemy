package com.trainingbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingbdi.springboot.app.item.clients.ProductsClientRest;
import com.trainingbdi.springboot.app.item.models.Item;
import com.trainingbdi.springboot.app.item.models.Products;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {
	
	@Autowired
	private ProductsClientRest clientFeign;

	@Override
	public List<Item> findAll() {
		return clientFeign.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findBy(Long id, Integer amount) {
		return new Item (clientFeign.details(id), amount);
	}

	@Override
	public Products save(Products products) {
		return clientFeign.create(products);
	}

	@Override
	public Products update(Products products, Long id) {
		return clientFeign.update(products, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.delete(id);
	}

}
