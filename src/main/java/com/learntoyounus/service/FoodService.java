package com.learntoyounus.service;

import com.learntoyounus.entity.Category;
import com.learntoyounus.entity.Food;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.request.FoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(FoodRequest foodRequest, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVegetarian, boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
