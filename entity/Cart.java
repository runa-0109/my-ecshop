package com.example.demo.entity;

import java.util.List;

import lombok.Data;

@Data
public class Cart {
	 private Integer id;   // カートのID
	 private String username;   // ユーザー名
	 private List<CartItem> items;   // カート内の商品リスト
}
