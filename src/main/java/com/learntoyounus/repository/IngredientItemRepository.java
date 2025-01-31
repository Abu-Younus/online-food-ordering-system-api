package com.learntoyounus.repository;

import com.learntoyounus.entity.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientsItem,Long> {
    List<IngredientsItem> findIngredientsItemByRestaurantId(Long id);
}
