package com.learntoyounus.service;

import com.learntoyounus.entity.Category;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        categoryRepository.save(category);

        return category;
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepository.findRestaurantById(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()) {
            throw new Exception("Category not found!");
        }

        return categoryOptional.get();
    }
}
