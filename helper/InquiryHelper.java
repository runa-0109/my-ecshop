package com.example.demo.helper;

import java.time.LocalDateTime;

import com.example.demo.entity.Inquiry;
import com.example.demo.form.InquiryForm;

public class InquiryHelper {
	public static InquiryForm convertInquiryForm(Inquiry inquiry) {
        InquiryForm form = new InquiryForm();
        form.setId(inquiry.getId());
        form.setUsername(inquiry.getUsername());
        form.setSubject(inquiry.getSubject());
        form.setMessage(inquiry.getMessage());
        form.setCreatedAt(inquiry.getCreatedAt());
        return form;
    }
	//OrderForm→Order(新規登録・更新)
	public static Inquiry convertInquiry(InquiryForm form,String username) {
		Inquiry inquiry = new Inquiry();
		inquiry.setId(form.getId());
		inquiry.setSubject(form.getSubject());
        inquiry.setMessage(form.getMessage());
        inquiry.setUsername(username);
        inquiry.setCreatedAt(LocalDateTime.now());
		return inquiry;
	}
}
