package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Inquiry {
	private Integer id;
	private String username;
    private String subject;        // 件名
    private String message;        // 内容
    private LocalDateTime createdAt; // 作成日時
    private Boolean isRead;
}
