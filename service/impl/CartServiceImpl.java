package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.CartItem;
import com.example.demo.repository.CartMapper;
import com.example.demo.repository.ProductMapper;
import com.example.demo.service.CartService;

import lombok.RequiredArgsConstructor;



@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
	private final CartMapper cartMapper;
	private final ProductMapper productMapper;

	@Override
	public Integer getCartIdByUsername(String username) {
        Integer cartId = cartMapper.findCartIdByUsername(username);
		if (cartId == null) {
            cartMapper.createCart(username);
            cartId = cartMapper.findCartIdByUsername(username);
        }
        return cartId;
	}

	@Override
	public void addToCart(String username, Integer productId) {
		Integer cartId = getCartIdByUsername(username);
        cartMapper.addToCart(cartId, productId);		
	}

	@Override
	public List<CartItem> getCartItems(String username) {
        return cartMapper.findCartItemsByUsername(username);
	}

	@Override
	public void removeFromCart(Integer cartItemId) {
        cartMapper.removeFromCart(cartItemId);
		
	}

	@Override
	public void clearCart(String username) {
        cartMapper.clearCart(username);		
	}

	@Override
	public Double calculateCartTotal(String username) {
		List<CartItem> items = getCartItems(username);
	    return items.stream()
	                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
	                .sum();
	}

	@Override
	public String changeQuantity(String username, Integer productId, Integer quantity) {
	    Integer cartId = cartMapper.findCartIdByUsername(username);
		if(quantity <= 0) {
			cartMapper.removeFromCart(productId);
			return null;
		}
		int stock = productMapper.selectStockById(productId);
		if(quantity > stock) {
			return "在庫を超える数量です(残り:"+stock+")";
		}
		cartMapper.updateQuantity(cartId,productId,quantity);
		return null;
	}
}
