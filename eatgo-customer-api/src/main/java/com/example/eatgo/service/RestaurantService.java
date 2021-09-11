package com.example.eatgo.service;

import com.example.eatgo.domain.*;
import com.example.eatgo.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsWithRegion(String region) {
        return restaurantRepository.findAllByAddressContaining(region);
    }

    public List<Restaurant> getRestaurantsWithCategoryId(Long categoryId) {
        return restaurantRepository.findAllByCategoryId(categoryId);
    }

    public List<Restaurant> getRestaurantsWithRegionAndCategoryId(String region, Long categoryId) {
        return restaurantRepository.findAllByAddressContainingAndCategoryId(region, categoryId);
    }

    public Restaurant getRestaurant(Long id){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
        menuItems.forEach(restaurant::addMenuItem);
        reviews.forEach(restaurant::addReview);
        return restaurant;
    }
}
