package com.learntoyounus.service;

import com.learntoyounus.entity.Order;
import com.learntoyounus.entity.User;
import com.learntoyounus.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception;

    public Order updateOrderStatus(Long orderId, String orderStatus) throws Exception;

    public Order cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
