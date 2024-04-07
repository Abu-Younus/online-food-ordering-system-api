package com.learntoyounus.controller;

import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.entity.User;
import com.learntoyounus.request.RestaurantRequest;
import com.learntoyounus.response.MessageResponse;
import com.learntoyounus.service.RestaurantService;
import com.learntoyounus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody RestaurantRequest restaurantRequest,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(restaurantRequest, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) throws Exception {
        Restaurant restaurant = restaurantService.updateRestaurant(id, restaurantRequest);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@PathVariable Long id) throws Exception {
        restaurantService.deleteRestaurant(id);
        MessageResponse response = new MessageResponse();
        response.setMessage("Restaurant deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id) throws Exception {
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> getRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
