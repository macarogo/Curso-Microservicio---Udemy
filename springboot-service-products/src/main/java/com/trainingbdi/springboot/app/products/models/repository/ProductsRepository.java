package com.trainingbdi.springboot.app.products.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.trainingbdi.springboot.app.commons.models.entity.Products;

public interface ProductsRepository extends CrudRepository<Products, Long>{

}
