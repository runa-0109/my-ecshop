package com.example.demo.helper;

import com.example.demo.entity.Product;
import com.example.demo.form.ProductForm;

/**
 * product:Helper
 */
public class ProductHelper {
	/**
	 * productへ変換
	 */
	public static Product convertProduct(ProductForm form) {
		Product product = new Product();
		product.setId(form.getId());
		product.setName(form.getName());
		product.setDescription(form.getDescription());
		product.setPrice(form.getPrice());
		product.setImageUrl(form.getImageUrl());
		product.setCategoryId(form.getCategoryId());
		product.setStock(form.getStock());
		return product;
	}
	/**
	 * productFormへの変換
	 */
	public static ProductForm convertProductForm(Product product) {
		ProductForm form = new ProductForm();
		form.setId(product.getId());
		form.setName(product.getName());
		form.setDescription(product.getDescription());
		form.setPrice(product.getPrice());
		form.setImageUrl(product.getImageUrl());
		form.setCategoryId(product.getCategoryId());
		form.setStock(product.getStock());
		//更新画面設定
		form.setIsNew(false);
		return form;
	}
}
