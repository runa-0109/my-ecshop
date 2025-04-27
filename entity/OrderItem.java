package com.example.demo.entity;

import lombok.Data;

@Data
public class OrderItem {
	private Integer id;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
	private double price;
}
