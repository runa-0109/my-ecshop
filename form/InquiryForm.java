package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryForm {
	private Integer id;
	private String username;
	@NotBlank(message = "件名は必須です")
	@Size(max = 30,message = "件名は30文字以内で入力してください")
    private String subject;        // 件名
	
	@NotBlank(message = "内容は必須です")
	@Size(max = 200,message = "内容は200文字以内で入力してください")
    private String message;        // 内容
    private LocalDateTime createdAt; // 作成日時
}
