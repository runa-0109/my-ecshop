package com.example.demo.helper;

import java.time.LocalDateTime;

import com.example.demo.entity.Order;
import com.example.demo.form.OrderForm;

/**
 * orderHelper
 */
public class OrderHelper {
	//Order→OrderForm(確認画面・編集画面)
	public static OrderForm convertOrderForm(Order order) {
        OrderForm form = new OrderForm();
        form.setId(order.getId());
        form.setUsername(order.getUsername());
        form.setRecipientName(order.getRecipientName());
        form.setPostalCode(order.getPostalCode());
        form.setAddress(order.getAddress());
        form.setPhoneNumber(order.getPhoneNumber());
        form.setPaymentMethod(order.getPaymentMethod());
        form.setPrice(order.getTotalPrice()); // ★ 追加
        return form;
    }
	//OrderForm→Order(新規登録・更新)
	public static Order convertOrder(OrderForm form) {
		Order order = new Order();
		order.setId(form.getId());
		order.setUsername(form.getUsername());
		order.setRecipientName(form.getRecipientName());
		order.setPostalCode(form.getPostalCode());
		order.setAddress(form.getAddress());
		order.setPhoneNumber(form.getPhoneNumber());
		order.setPaymentMethod(form.getPaymentMethod());
		order.setOrderDate(LocalDateTime.now()); // 登録時のみ
		order.setTotalPrice(form.getPrice()); // ★ 追加
		return order;
	}
	
}
