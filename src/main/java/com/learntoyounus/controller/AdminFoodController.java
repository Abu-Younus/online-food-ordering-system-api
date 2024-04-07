package com.learntoyounus.controller;

import com.learntoyounus.entity.Food;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.entity.User;
import com.learntoyounus.request.FoodRequest;
import com.learntoyounus.response.MessageResponse;
import com.learntoyounus.service.FoodService;
import com.learntoyounus.service.RestaurantService;
import com.learntoyounus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<Food> createFood(@RequestBody FoodRequest foodRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(foodRequest.getRestaurantId());
        Food food = foodService.createFood(foodRequest, foodRequest.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) throws Exception {
        foodService.deleteFood(id);
        MessageResponse response = new MessageResponse();
        response.setMessage("Food deleted successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Food> updateAvailabilityStatus(@PathVariable Long id) throws Exception {
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
