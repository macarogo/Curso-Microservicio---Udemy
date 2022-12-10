package com.trainingbdi.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trainingbdi.springboot.app.item.models.Item;
import com.trainingbdi.springboot.app.item.models.Products;
import com.trainingbdi.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@GetMapping("/list")
	public List<Item> lisst(){
		return itemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod="metodoAlternativo")
	@GetMapping("/products/{id}/amount/{amount}")
	public Item detail(@PathVariable Long id, @PathVariable Integer amount) {
		return itemService.findBy(id, amount);
	}
	
	public Item metodoAlternativo(@PathVariable Long id, @PathVariable Integer amount) {
		Item item= new Item();
		Products products= new Products();
		
		item.setAmount(amount);
		products.setId(id);
		products.setName("Camara sony");
		products.setPrice(500.00);
		item.setProducts(products);
		return item;
	}

}
