package com.learntoyounus.service;

import com.learntoyounus.entity.*;
import com.learntoyounus.repository.AddressRepository;
import com.learntoyounus.repository.OrderItemRepository;
import com.learntoyounus.repository.OrderRepository;
import com.learntoyounus.repository.UserRepository;
import com.learntoyounus.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception {
        Address shippedAddress = orderRequest.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippedAddress);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(orderRequest.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreated_at(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItem()) {
            OrderItem item = new OrderItem();
            item.setFood(cartItem.getFood());
            item.setIngredients(cartItem.getIngredients());
            item.setQuantity(cartItem.getQuantity());
            item.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(item);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrderStatus(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (
            orderStatus.equals("OUT_FOR_DELIVERY")
            || orderStatus.equals("DELIVERED")
            || orderStatus.equals("COMPLETED")
            || orderStatus.equals("PENDING")
        ) {
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        } else {
            throw new Exception("Please select valid order status!");
        }
        return order;
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(order.getId());

        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()) {
            throw new Exception("Order not found");
        }
        return orderOptional.get();
    }
}
