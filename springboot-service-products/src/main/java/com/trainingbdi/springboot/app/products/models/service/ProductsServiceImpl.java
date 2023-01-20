package com.trainingbdi.springboot.app.products.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingbdi.springboot.app.commons.models.entity.Products;
import com.trainingbdi.springboot.app.products.models.repository.ProductsRepository;

@Service
public class ProductsServiceImpl implements IProductsService {

	@Autowired
	private ProductsRepository productsRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Products> findAll() {
		return (List<Products>)productsRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Products findById(Long id) {
		return productsRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional()
	public Products save(Products products) {
		return productsRepository.save(products);
	}

	@Override
	@Transactional()
	public void deleteById(Long id) {
		productsRepository.deleteById(id);
	}
}
