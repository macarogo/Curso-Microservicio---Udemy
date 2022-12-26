package com.trainingbdi.springboot.app.products.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.trainingbdi.springboot.app.products.models.entity.Products;
import com.trainingbdi.springboot.app.products.models.service.IProductsService;

@RestController
public class ProductsController {
	
	@Autowired
	private Environment environment;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductsService productsService;
	
	@GetMapping("/list")
	public List<Products> list(){
		return productsService.findAll().stream().map(products ->{
			products.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
			//products.setPort(port);
			return products;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/products/{id}")
	public Products details(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)){
			throw new IllegalStateException("Product not found");
		}
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Products products=productsService.findById(id);
		products.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		//products.setPort(port);
		
		return products;
	}
	
}
