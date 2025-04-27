package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CartItem;

public interface CartService {
	//特定のユーザーのカートの情報
	 Integer getCartIdByUsername(String username);
	 void addToCart(String username, Integer productId);
	 List<CartItem> getCartItems(String username);
	 void removeFromCart(Integer cartItemId);
	 void clearCart(String username);
	 //商品の合計金額
	 Double calculateCartTotal(String username);
	 String changeQuantity(String name,Integer productId,Integer quantity);
}
