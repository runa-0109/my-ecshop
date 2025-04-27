package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	private Integer id;
	private String username;
	private LocalDateTime orderDate;
	private double totalPrice;
	
	// 宛名
	private String recipientName;     
	// 郵便番号
	private String postalCode;        
	// 住所
	private String address;           
	// 電話番号
	private String phoneNumber;       
	// 支払い方法
	private String paymentMethod;  
	
	private List<OrderItem> items;	
	private Integer status;

}
