package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product;


public interface ProductService {
	List<Product> findAllProduct();
	
	Product findById(Integer id);
	
	void insertProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProduct(Integer id);
	
    List<Product> findByCategoryId(Integer categoryId);
    
    List<Product> getLatestProducts();
    List<Product> getPopularProducts();
}
