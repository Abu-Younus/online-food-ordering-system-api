package com.learntoyounus.request;

import com.learntoyounus.entity.Category;
import com.learntoyounus.entity.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class FoodRequest {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;

    private List<IngredientsItem> ingredientsItems;

}
