package com.example.demo.entity;

import lombok.Data;

@Data
public class CartItem {
    private Integer id;   // カートアイテムID
    private Integer cartId;   // CartのID（外部キー）
    private Integer productId;   // 商品のID（外部キー）
    private Integer quantity;   // 数量
    private Product product; //Productエンティティ(商品情報)
    private double subtotal; // 小計 (商品価格 × 数量)
}
