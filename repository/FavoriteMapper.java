package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Favorite;

@Mapper
public interface FavoriteMapper {
	//お気に入りに追加
	void insertFavorite(@Param("username") String username,@Param("productId") Integer productId);
	//お気に入り解除
	void deleteFavorite(@Param("username") String username,@Param("favoriteId") Integer favoriteId);
	//一覧取得
	List<Favorite> selectFavoritesByUserId(@Param("username") String username);
}
