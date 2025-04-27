package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.form.OrderForm;

public interface OrderService {
	// 注文の登録（カート情報を渡して注文を作成）
    void placeOrder(String username, List<CartItem> cartItems,OrderForm form);

    //商品の合計金額を計算(送料込み)
    Double calculateFinalAmount(String username);
    // ユーザーの注文履歴を取得
    List<Order> getOrdersByUsername(String username);
    Order findOrderDetailById(Integer id);
    List<Order> findOrderItemByOrderId(Integer orderId);
    //admin用実装注文履歴
    List<Order> findAllOrders();
    Order findOrderById(Integer id);
    //admin用status
    void updateOrderStatus(Integer orderId,Integer status);
    List<OrderDetail> findOrderDetailsByOrderId(Integer orderId);

}
