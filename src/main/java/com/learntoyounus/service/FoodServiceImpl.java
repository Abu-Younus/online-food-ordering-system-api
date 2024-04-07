package com.learntoyounus.service;

import com.learntoyounus.entity.Category;
import com.learntoyounus.entity.Food;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.repository.FoodRepository;
import com.learntoyounus.request.FoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(FoodRequest foodRequest, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(foodRequest.getDescription());
        food.setImages(foodRequest.getImages());
        food.setName(foodRequest.getName());
        food.setPrice(foodRequest.getPrice());
        food.setIngredientsItems(foodRequest.getIngredientsItems());
        food.setSeasonal(foodRequest.isSeasonal());
        food.setVegetarian(foodRequest.isVegetarian());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVegetarian, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findRestaurantById(restaurantId);
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonVegetarian) {
            foods = filterByNonVegetarian(foods, isNonVegetarian);
        }
        if(isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if(foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
           if(food.getFoodCategory() != null) {
               return food.getFoodCategory().getName().equals(foodCategory);
           }
           return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVegetarian(List<Food> foods, boolean isNonVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if(foodOptional.isEmpty()) {
            throw new Exception("Food doesn't exist!");
        }
        return foodOptional.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;
    }
}
