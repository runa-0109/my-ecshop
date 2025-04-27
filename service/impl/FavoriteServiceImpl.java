package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Favorite;
import com.example.demo.repository.FavoriteMapper;
import com.example.demo.service.FavoriteService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService{
	private final FavoriteMapper favoriteMapper;

	@Override
	public void addFavorite(String username, Integer productId) {
		favoriteMapper.insertFavorite(username,productId);
	}

	@Override
	public void removeFavorite(String username, Integer favoriteId) {
		favoriteMapper.deleteFavorite(username, favoriteId);
	}

	@Override
	public List<Favorite> getFavoriteByUserId(String username) {
		return favoriteMapper.selectFavoritesByUserId(username);
	}

}
