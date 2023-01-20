package com.trainingbdi.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.trainingbdi.springboot.app.commons.models.entity.Products;

@FeignClient(name = "service-products")
public interface ProductsClientRest {
	
	@GetMapping("/list")
	public List<Products> list();
	
	@GetMapping("/products/{id}")
	public Products details(@PathVariable Long id);
	
	@PostMapping("/create")
	public Products create(@RequestBody Products products);
	
	@PutMapping("/update/{id}")
	public Products update(@RequestBody Products products,@PathVariable Long id);
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id);
}
