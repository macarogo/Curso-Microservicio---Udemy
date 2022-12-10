package com.trainingbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trainingbdi.springboot.app.item.models.Item;
import com.trainingbdi.springboot.app.item.models.Products;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
		List<Products> getListProducts= Arrays.asList(clientRest.getForObject("http://service-products/list", Products[].class));
		return getListProducts.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findBy(Long id, Integer amount) {
		Map<String, String> pathVarible= new HashMap<String, String>();
		pathVarible.put("id", id.toString());
		Products products= clientRest.getForObject("http://service-products/products/{id}", Products.class, pathVarible);
		return new Item(products, amount);
	}

}
