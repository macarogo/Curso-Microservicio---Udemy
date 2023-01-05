package com.trainingbdi.springboot.app.products.models.service;

import java.util.List;

import com.trainingbdi.springboot.app.products.models.entity.Products;

public interface IProductsService {
	
	public List<Products>findAll();
	public Products findById(Long id);
	public Products save(Products products);
	public void deleteById(Long id);
}
