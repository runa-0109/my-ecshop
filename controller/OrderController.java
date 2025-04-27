package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.form.OrderForm;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	private final CartService cartService;
	private final OrderService orderService;
	//購入情報入力画面
	@GetMapping("/form")
	public String showOrderForm(Model model) {
		model.addAttribute("orderForm",new OrderForm());
		return "user/order-form";
	}
	//購入情報確認画面
	@PostMapping("/confirm")
	public String confirmOrder(@Valid @ModelAttribute OrderForm orderForm,BindingResult result, Model model) {
		// エラー時は元のフォーム画面に戻す
		if (result.hasErrors()) {
	        return "user/order-form"; 
	    }
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName(); 
		orderForm.setUsername(username);
		
        List<CartItem> cartItems = cartService.getCartItems(username);
        // 送料を含めた合計金額を計算
        double totalAmountWithShipping = orderService.calculateFinalAmount(username);
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("orderForm", orderForm);  // 入力した購入情報も確認用に渡す
        model.addAttribute("totalAmountWithShipping", totalAmountWithShipping);  // 合計金額（送料込み）をビューに渡す
        orderForm.setPrice(totalAmountWithShipping);
        System.out.println("=== 確認画面用 合計金額 === " + orderForm.getPrice());

        return "user/order-confirm";  // 購入情報確認画面
    }
	
	
	
	
	@PostMapping("/create")
	public String createOrder(@ModelAttribute OrderForm orderForm, Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    // デバッグ用ログ
	    System.out.println("== DB登録前の注文情報 ==");
	    System.out.println("宛名: " + orderForm.getRecipientName());
	    System.out.println("住所: " + orderForm.getAddress());
	    System.out.println("電話番号: " + orderForm.getPhoneNumber());
	    System.out.println("支払い方法: " + orderForm.getPaymentMethod());
	    System.out.println("合計金額: " + orderForm.getPrice());
	    // 1. カート内の商品取得
	    List<CartItem> cartItems = cartService.getCartItems(username);
	    if (cartItems.isEmpty()) {
	        model.addAttribute("error", "カートが空です。");
	        return "redirect:/cart";
	    }
	    try {
	    	//注文確定
	        orderService.placeOrder(username, cartItems,orderForm); 
	    } catch (RuntimeException e) {
	        model.addAttribute("error", e.getMessage());
	        return "user/order-confirm";
	    }
	    return "user/checkout";  // ご購入ありがとうございました画面
	}
	  //注文履歴を取得する
    @GetMapping("/list")
    public String showUserOrders(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    List<Order> orders = orderService.getOrdersByUsername(username);
	    model.addAttribute("orderList",orders);
    	return "user/order-list";
    }
    //特定のIDの注文履歴を取得する
    @GetMapping("/list/detail/{id}")
    public String showUserOrderDetail(@PathVariable Integer id,Model model) {
    	model.addAttribute("order",orderService.findOrderDetailById(id));
    	model.addAttribute("detailList",orderService.findOrderItemByOrderId(id));
    	return "user/order-detail";
    }
}
