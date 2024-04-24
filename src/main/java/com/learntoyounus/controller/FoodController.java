package com.learntoyounus.controller;

import com.learntoyounus.entity.Food;
import com.learntoyounus.service.FoodService;
import com.learntoyounus.service.RestaurantService;
import com.learntoyounus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword) {
        List<Food> foods = foodService.searchFood(keyword);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantsFood(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) boolean isVegetarian,
            @RequestParam(required = false) boolean isNonVegetarian,
            @RequestParam(required = false) boolean isSeasonal,
            @RequestParam String foodCategory
            ) {
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, isVegetarian, isNonVegetarian, isSeasonal, foodCategory);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
