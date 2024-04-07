package com.learntoyounus.controller;

import com.learntoyounus.entity.Order;
import com.learntoyounus.entity.User;
import com.learntoyounus.service.OrderService;
import com.learntoyounus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getRestaurantOrders(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus
    ) throws Exception {
        List<Order> orders = orderService.getRestaurantOrders(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus
    ) throws Exception {
        Order order = orderService.updateOrderStatus(id, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
