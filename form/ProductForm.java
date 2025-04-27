package com.example.demo.form;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {
	private Integer id;
	//商品名
    @NotBlank(message = "商品名を入力してください")
	private String name;
	//説明
    @NotBlank(message = "商品説明を入力してください")
	private String description;
	//価格
    @PositiveOrZero(message = "価格は0以上で入力してください")
	private double price;
	//写真
	private String imageUrl;
	//カテゴリID
    @NotNull(message = "カテゴリを選択してください")
	private Integer categoryId;
	//数量
    @NotNull(message = "在庫数を入力してください")
    @Min(value = 0, message = "在庫数は0以上で入力してください")
	private Integer stock;

	//新規判定
	private Boolean isNew;
	//ファイルをMultipartFile型で受け取る
	private MultipartFile imageFile;
}