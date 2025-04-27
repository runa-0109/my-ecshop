package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.OrderItem;

@Mapper
public interface OrderMapper {
	// 注文を追加
    void insertOrder(Order order);

    // 注文アイテムを追加
    void insertOrderItem(OrderItem item);

    // 特定ユーザーの注文履歴を取得
    List<Order> findOrdersByUsername(String username);
    List<Order> findOrderItemByOrderId(Integer Orderid);
    Order findOrderDetailById(Integer id);
    //admin用実装注文履歴
    List<Order> findAllOrders();
    void updateOrderStatus(@Param("orderId") Integer orderId, @Param("status") Integer status);
    List<OrderDetail> findOrderDetailsByOrderId(Integer orderId);
    Order findOrderById(Integer id);

}
