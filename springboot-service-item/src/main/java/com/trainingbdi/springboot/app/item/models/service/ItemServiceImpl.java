package com.trainingbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trainingbdi.springboot.app.item.models.Item;
import com.trainingbdi.springboot.app.commons.models.entity.Products;

@Service("serviceRestTemplate")
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

	@Override
	public Products save(Products products) {
		HttpEntity<Products> body= new HttpEntity<Products>(products);
		ResponseEntity<Products> response=clientRest.exchange("http://service-products/create", HttpMethod.POST, body, Products.class);
		Products productsResponse= response.getBody();
		return productsResponse;
	}

	@Override
	public Products update(Products products, Long id) {
		HttpEntity<Products> body= new HttpEntity<Products>(products);
		Map<String, String> pathVarible= new HashMap<String, String>();
		pathVarible.put("id", id.toString());
		ResponseEntity<Products> response= clientRest.exchange("http://service-products/update/{id}", 
				HttpMethod.PUT, body, Products.class,pathVarible);
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVarible= new HashMap<String, String>();
		pathVarible.put("id", id.toString());
		clientRest.delete("http://service-products/delete/{id}", pathVarible);
		
	}

}
