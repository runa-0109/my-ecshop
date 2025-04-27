package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Favorite;

public interface FavoriteService {
	//お気に入りに追加
	void addFavorite(String username,Integer productId);
	//お気に入りを解除
	void removeFavorite(String username,Integer favoriteId);
	//お気に入り一覧の取得
	List<Favorite> getFavoriteByUserId(String username);
}
