package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Inquiry;
import com.example.demo.repository.InquiryMapper;
import com.example.demo.service.InquiryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryServiceImpl implements InquiryService{
	private final InquiryMapper inquiryMapper;

    @Override
    public void saveInquiry(Inquiry inquiry) {
        inquiryMapper.insertInquiry(inquiry);
    }

    @Override
    public List<Inquiry> findAll() {
        return inquiryMapper.findAll();
    }

    @Override
    public Inquiry findById(Integer id) {
        return inquiryMapper.findById(id);
    }

	@Override
	public void markAsRead(Integer id) {
		inquiryMapper.markAsRead(id);
	}

	
}
