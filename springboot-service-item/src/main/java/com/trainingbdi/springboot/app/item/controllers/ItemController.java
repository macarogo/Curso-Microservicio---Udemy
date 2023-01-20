package com.trainingbdi.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trainingbdi.springboot.app.item.models.Item;
import com.trainingbdi.springboot.app.commons.models.entity.Products;
import com.trainingbdi.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
public class ItemController {
	
	private final Logger logger= LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@Value("${configurations.text}")
	private String text;
	
	@GetMapping("/list")
	public List<Item> lisst(@RequestParam(name="name", required= false)String name, @RequestHeader(name="token-request", required= false)String token){
		System.out.println(name);
		System.out.println(token);
		return itemService.findAll();
	}
	
	@GetMapping("/products/{id}/amount/{amount}")
	public Item detail(@PathVariable Long id, @PathVariable Integer amount) {
		return cbFactory.create("items")
				.run(()-> itemService.findBy(id, amount), e -> metodoAlternativo(id, amount, e));
	}
	
	@CircuitBreaker(name= "items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/products2/{id}/amount/{amount}")
	public Item detail2(@PathVariable Long id, @PathVariable Integer amount) {
		return itemService.findBy(id, amount);
	}
	
	@CircuitBreaker(name= "items", fallbackMethod = "metodoAlternativo2")
	@TimeLimiter(name= "items")
	@GetMapping("/products3/{id}/amount/{amount}")
	public CompletableFuture<Item> detail3(@PathVariable Long id, @PathVariable Integer amount) {
		return CompletableFuture.supplyAsync(() -> itemService.findBy(id, amount));
	}
	
	public Item metodoAlternativo(@PathVariable Long id, @PathVariable Integer amount, Throwable e) {
		logger.info(e.getMessage());
		Item item= new Item();
		Products products= new Products();
		
		item.setAmount(amount);
		products.setId(id);
		products.setName("Camara sony");
		products.setPrice(500.00);
		item.setProducts(products);
		return item;
	}
	
	public CompletableFuture<Item> metodoAlternativo2(@PathVariable Long id, @PathVariable Integer amount, Throwable e) {
		logger.info(e.getMessage());
		Item item= new Item();
		Products products= new Products();
		
		item.setAmount(amount);
		products.setId(id);
		products.setName("Camara sony");
		products.setPrice(500.00);
		item.setProducts(products);
		return CompletableFuture.supplyAsync(() -> item);
	}
	
	@GetMapping("/get-config")
	public ResponseEntity<?> getConfig(@Value("${server.port}") String port){
		logger.info(text);
		
		Map<String, String> json= new HashMap<>();
		json.put("text", text);
		json.put("port", port);
		
		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.name", env.getProperty("configurations.autor.name"));
			json.put("autor.email", env.getProperty("configurations.autor.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Products create(@RequestBody Products products) {
		return itemService.save(products);
	}
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Products updateProd(@RequestBody Products products, @PathVariable Long id) {
		return itemService.update(products, id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}

}
