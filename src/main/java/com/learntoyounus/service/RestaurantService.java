package com.learntoyounus.service;

import com.learntoyounus.domain.RestaurantDto;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.entity.User;
import com.learntoyounus.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest, User user);

    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
