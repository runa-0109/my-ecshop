package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Inquiry;
import com.example.demo.form.InquiryForm;
import com.example.demo.helper.InquiryHelper;
import com.example.demo.service.InquiryService;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class UserController {
	private final InquiryService inquiryService;
	private final ProductService productService;
	@GetMapping("/home")
	public String getAdminDashboard(Model model) {
        model.addAttribute("latestProducts", productService.getLatestProducts());
        model.addAttribute("popularProducts", productService.getPopularProducts());
		return "user/home";
	}
	@GetMapping("/inquiry/form")
	public String showInquiryForm(Model model) {
		model.addAttribute("inquiryForm",new InquiryForm());
		return "user/inquiry-form";
	}
	
	@PostMapping("/inquiry/submit")
	public String submitInquiry(@Valid @ModelAttribute InquiryForm form,BindingResult result) {
		if (result.hasErrors()) {
			return "user/inquiry-form";  // エラー時は元のフォーム画面に戻す必要あり
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    Inquiry inquiry = InquiryHelper.convertInquiry(form, username);
	    inquiryService.saveInquiry(inquiry);

	    return "redirect:/home";
	}
}

