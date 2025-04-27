package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private Integer id;
	//商品名
	private String name;
	//説明
	private String description;
	//価格
	private double price;
	private String imageUrl;
	private Integer categoryId;
	private Integer stock;
    private String categoryName;
    
    private Integer totalQuantity;

}
