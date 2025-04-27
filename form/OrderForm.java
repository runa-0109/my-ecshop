package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {
	private Integer id;
	private String username;
	@NotBlank(message = "宛名は必須です")
    @Size(max = 50, message = "宛名は50文字以内で入力してください")
	private String recipientName;
	
	@NotBlank(message = "郵便番号は必須です")
    @Pattern(regexp = "\\d{3}-\\d{4}", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String postalCode;
	
    @NotBlank(message = "住所は必須です")
	private String address;
    
    @NotBlank(message = "電話番号は必須です")
    @Pattern(regexp = "\\d{10,11}", message = "電話番号は10～11桁の数字で入力してください")
	private String phoneNumber;
    
    @NotBlank(message = "支払い方法を選択してください")
	private String paymentMethod;
	private double price;
}
