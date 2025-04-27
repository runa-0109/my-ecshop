package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.CartItem;

@Mapper
public interface CartMapper {
	 // ユーザーのカートIDを取得
    Integer findCartIdByUsername(String username);

    // カートを作成
    void createCart(String username);

    // ユーザーのカート内の商品を取得
    List<CartItem> findCartItemsByUsername(String username);

    // カートに商品を追加（初回追加時は1個）
    void addToCart(@Param("cartId") Integer cartId, @Param("productId") Integer productId);

    //カート内の商品の数量を変更
    void updateQuantity(@Param("cartId") Integer cartId,@Param("productId") Integer productId,@Param("quantity") Integer quantity);
    // カートアイテムを削除
    void removeFromCart(Integer id);

    // カートをクリア
    void clearCart(String username);

}
