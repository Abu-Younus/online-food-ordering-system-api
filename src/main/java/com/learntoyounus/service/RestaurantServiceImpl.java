package com.learntoyounus.service;

import com.learntoyounus.domain.RestaurantDto;
import com.learntoyounus.entity.Address;
import com.learntoyounus.entity.Restaurant;
import com.learntoyounus.entity.User;
import com.learntoyounus.repository.AddressRepository;
import com.learntoyounus.repository.RestaurantRepository;
import com.learntoyounus.repository.UserRepository;
import com.learntoyounus.request.RestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest, User user) {
        Address address = addressRepository.save(restaurantRequest.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(restaurantRequest.getContactInformation());
        restaurant.setCuisineType(restaurantRequest.getCuisineType());
        restaurant.setDescription(restaurantRequest.getDescription());
        restaurant.setImages(restaurantRequest.getImages());
        restaurant.setName(restaurantRequest.getName());
        restaurant.setOpeningHours(restaurantRequest.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurantRepository.save(restaurant);

        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(restaurantRequest.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(restaurantRequest.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(restaurantRequest.getName());
        }
        restaurantRepository.save(restaurant);

        return restaurant;
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new Exception("Restaurant is not found with id " + restaurantId);
        }
        return restaurantOpt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findRestaurantByOwnerId(userId);
        if(restaurant == null) {
            throw new Exception("Restaurant not found with owner id " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        boolean isFavourite = false;

        List<RestaurantDto> favourites = user.getFavourites();
        for (RestaurantDto favourite : favourites) {
            if (favourite.getId() == restaurantId) {
                isFavourite = true;
                break;
            }
        }

        //If the restaurant is already added to favourites, removed it, otherwise add it to favourites
        if(isFavourite) {
            favourites.removeIf(favourite -> favourite.getId() == restaurantId);
        } else {
            favourites.add(restaurantDto);
        }

        userRepository.save(user);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);

        return restaurant;
    }
}
