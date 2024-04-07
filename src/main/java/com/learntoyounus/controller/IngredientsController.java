package com.learntoyounus.controller;

import com.learntoyounus.entity.IngredientCategory;
import com.learntoyounus.entity.IngredientsItem;
import com.learntoyounus.request.IngredientCategoryRequest;
import com.learntoyounus.request.IngredientRequest;
import com.learntoyounus.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest ingredientCategoryRequest
    ) throws Exception {
        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(ingredientCategoryRequest.getName(),
                ingredientCategoryRequest.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<IngredientsItem> createIngredientsItem(
            @RequestBody IngredientRequest ingredientRequest
    ) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.createIngredientsItem(ingredientRequest.getName(),
                ingredientRequest.getRestaurantId(),
                ingredientRequest.getCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.updateStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> findRestaurantIngredients(@PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientsItems = ingredientsService.findRestaurantIngredients(id);

        return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> findIngredientCategoryByRestaurantId(@PathVariable Long id) throws Exception {
        List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
