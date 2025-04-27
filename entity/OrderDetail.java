package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDetail {
	private Integer orderId;
    private String username;
    private LocalDateTime orderDate;
    private Integer status;

    private Integer productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private Integer subtotal;
    
    private String  recipientName;
    private String postalCode;
    private String  address;
    private String  phoneNumber;
    private String  paymentMethod;
}
