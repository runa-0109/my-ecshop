package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {
	//全ての商品を取得
		List<Product> selectAll();
		//指定されたIDの商品を取得
		Product selectById(@Param("id") Integer id);
		//商品の更新
		void insert(Product product);
		//商品の追加
		void update(Product product);
		//特定のidの商品の削除
		void delete(@Param("id") Integer id);
		//指定された商品の在庫数を取得
		Integer selectStockById(@Param("productId") Integer productId);
		//指定された商品の在庫数を更新
		void updateStock(@Param("productId") Integer productId,@Param("quantity") Integer quantity);
		//カテゴリ別商品の取得
		List<Product> findByCategoryId(Integer categoryId);
		//新着商品の取得
	    List<Product> selectLatestProducts();
	    //人気商品の取得
	    List<Product> selectPopularProducts();


}
