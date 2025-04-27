package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//ログイン画面
@Controller
@RequestMapping("/")
public class MenuController {
	@GetMapping
	public String showMenu() {
		return "menu";
	}
	
}
