package com.learntoyounus.repository;

import com.learntoyounus.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findRestaurantById(Long id);
}
