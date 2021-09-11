package com.example.eatgo.service;

import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.domain.RestaurantRepository;
import com.example.eatgo.dto.RestaurantDto;
import com.example.eatgo.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Long id){
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public Restaurant addRestaurant(RestaurantDto.Request requestData) {
        Restaurant restaurant = RestaurantDto.requestToEntity(requestData);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, RestaurantDto.Request requestData) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        if(!requestData.getName().isEmpty()){
            restaurant.updateName(requestData.getName());
        }
        if(!requestData.getAddress().isEmpty()){
            restaurant.updateAddress(requestData.getAddress());
        }

        return restaurant;
    }
}
