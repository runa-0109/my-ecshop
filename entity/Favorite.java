package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Favorite {
	private Integer id;
	private String username;
	private Integer productId;
	private LocalDateTime createdAt;
	private Product product;
}
