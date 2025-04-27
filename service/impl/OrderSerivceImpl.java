package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.OrderItem;
import com.example.demo.form.OrderForm;
import com.example.demo.repository.OrderMapper;
import com.example.demo.repository.ProductMapper;
import com.example.demo.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderSerivceImpl implements OrderService {
	private final OrderMapper orderMapper;
	private final CartServiceImpl cartServiceImpl;
	private final ProductMapper productMapper;
	@Override
	public void placeOrder(String username, List<CartItem> cartItems,OrderForm form) {
		Order order = new Order();
		order.setUsername(username);
		order.setOrderDate(LocalDateTime.now());
		double totalAmount = calculateFinalAmount(username);  // 合計金額計算
		order.setTotalPrice((int) totalAmount); 
		// 2. orders テーブルに保存 → 自動生成された ID を取得
        // 配送先情報をセット
        order.setRecipientName(form.getRecipientName());
        order.setPostalCode(form.getPostalCode());
        order.setAddress(form.getAddress());
        order.setPhoneNumber(form.getPhoneNumber());
        order.setPaymentMethod(form.getPaymentMethod());

        orderMapper.insertOrder(order);
        // 3. 各カートアイテムを order_item に保存
        for (CartItem cartItem : cartItems) {
        	//在庫のチェックと在庫数の更新
        	int stock = productMapper.selectStockById(cartItem.getProductId());
        	if (cartItem.getQuantity() > stock) {
        		throw new RuntimeException(cartItem.getProduct().getName() + " の在庫が足りません");
        	}
        	productMapper.updateStock(cartItem.getProductId(), cartItem.getQuantity());
        	//注文詳細の作成
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(cartItem.getProductId());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
         
            orderMapper.insertOrderItem(item);
        }
        cartServiceImpl.clearCart(username);  
	}
	//特定のユーザーの注文履歴を取得したい時に使うメソッド
	@Override
	public List<Order> getOrdersByUsername(String username) {
	    return orderMapper.findOrdersByUsername(username);

	}
	@Override
	public Double calculateFinalAmount(String username) {
		 double cartTotal = cartServiceImpl.calculateCartTotal(username);
		    double shippingFee = 1000; // 全国一律送料
		    return cartTotal + shippingFee; // 最終合計金額
	}
	@Override
	public List<Order> findAllOrders() {
		return orderMapper.findAllOrders();
	}
	@Override
	public Order findOrderDetailById(Integer id) {
		return orderMapper.findOrderDetailById(id);
	}
	@Override
	public List<Order> findOrderItemByOrderId(Integer orderId) {
		return orderMapper.findOrderItemByOrderId(orderId);
	}
	@Override
	public void updateOrderStatus(Integer orderId, Integer status) {
	    orderMapper.updateOrderStatus(orderId, status);

	}
	@Override
	public List<OrderDetail> findOrderDetailsByOrderId(Integer orderId) {
        return orderMapper.findOrderDetailsByOrderId(orderId);
	}
	@Override
	public Order findOrderById(Integer id) {
	    return orderMapper.findOrderById(id);
	}

}
