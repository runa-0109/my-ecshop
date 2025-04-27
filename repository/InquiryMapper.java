package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Inquiry;

@Mapper
public interface InquiryMapper {
    // お問い合わせを保存する
    void insertInquiry(Inquiry inquiry);

    // 全問い合わせを取得（admin用）
    List<Inquiry> findAll();

    // IDで問い合わせを取得（admin用）
    Inquiry findById(Integer id);
    
    //既読・未読
    void markAsRead(Integer id);


}
