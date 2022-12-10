package com.trainingbdi.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trainingbdi.springboot.app.item.models.Products;

@FeignClient(name = "service-products")
public interface ProductsClientRest {
	
	@GetMapping("/list")
	public List<Products> list();
	
	@GetMapping("/products/{id}")
	public Products details(@PathVariable Long id);
}
