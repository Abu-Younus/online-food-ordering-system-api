package com.learntoyounus.service;

import com.learntoyounus.entity.IngredientCategory;
import com.learntoyounus.entity.IngredientsItem;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.repository.IngredientCategoryRepository;
import com.learntoyounus.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService{

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategoryRepository.save(ingredientCategory);

        return ingredientCategory;
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategoryOptional = ingredientCategoryRepository.findById(id);
        if (ingredientCategoryOptional.isEmpty()) {
            throw new Exception("Ingredient Category not found!");
        }

        return ingredientCategoryOptional.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findRestaurantById(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(String ingredientName, Long restaurantId, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);

        IngredientsItem createdIngredientItem = ingredientItemRepository.save(ingredientsItem);
        ingredientCategory.getIngredients().add(createdIngredientItem);

        return createdIngredientItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> ingredientsItemOptional = ingredientItemRepository.findById(id);
        if (ingredientsItemOptional.isEmpty()) {
            throw new Exception("Ingredient Item not found!");
        }
        IngredientsItem ingredientsItem = ingredientsItemOptional.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
