//package com.example.demo.service.impl;
//
//import java.io.IOException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import com.example.demo.entity.LoginUser;
//
//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		// TODO 自動生成されたメソッド・スタブ
//		// セッションを取得
//		HttpSession session = request.getSession();
//		
//		// ログインしたユーザー情報を取得（UserDetails を継承した LoginUser を想定）
//		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//		
//		// ユーザー名をセッションに格納
//		session.setAttribute("username", loginUser.getUsername());
//		
//		// リダイレクト（元々のページまたはトップページへ）
//		response.sendRedirect("/");
//		
//	}
//}
