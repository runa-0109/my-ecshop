package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Inquiry;

public interface InquiryService {
	// お問い合わせを保存（ユーザー用）
    void saveInquiry(Inquiry inquiry);

    // 一覧取得（管理者用）
    List<Inquiry> findAll();

    // 詳細取得（管理者用）
    Inquiry findById(Integer id);
    void markAsRead(Integer id);

}
