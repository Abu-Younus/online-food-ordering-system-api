package com.learntoyounus.controller;

import com.learntoyounus.entity.Order;
import com.learntoyounus.entity.User;
import com.learntoyounus.request.OrderRequest;
import com.learntoyounus.response.PaymentResponse;
import com.learntoyounus.service.OrderService;
import com.learntoyounus.service.PaymentService;
import com.learntoyounus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(orderRequest, user);
        PaymentResponse response = paymentService.createPaymentLink(order);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUserOrders(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
