package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductMapper;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImple implements ProductService {
	private final ProductMapper productMapper;

	@Override
	public List<Product> findAllProduct() {
		return productMapper.selectAll();
	}

	@Override
	public Product findById(Integer id) {
		return productMapper.selectById(id);
	}

	@Override
	public void insertProduct(Product product) {
		productMapper.insert(product);
	}

	@Override
	public void updateProduct(Product product) {
		productMapper.update(product);
	}

	@Override
	public void deleteProduct(Integer id) {
		productMapper.delete(id);
	}

	@Override
	public List<Product> findByCategoryId(Integer categoryId) {
		return productMapper.findByCategoryId(categoryId);
	}

	@Override
	public List<Product> getLatestProducts() {
        return productMapper.selectLatestProducts();

	}

	@Override
	public List<Product> getPopularProducts() {
        return productMapper.selectPopularProducts();

	}
	
}

