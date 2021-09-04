package com.example.eatgo.controller;

import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> listWithoutParams(){
        return restaurantService.getRestaurants();
    }

    @GetMapping(value = "/restaurants", params = "region")
    public List<Restaurant> listWithRegion(@RequestParam String region){
        return restaurantService.getRestaurantsWithRegion(region);
    }

    @GetMapping(value = "/restaurants", params = "category")
    public List<Restaurant> listWithCategoryId(@RequestParam(value = "category") Long categoryId){
        return restaurantService.getRestaurantsWithCategoryId(categoryId);
    }

    @GetMapping(value = "/restaurants", params = {"region", "category"})
    public List<Restaurant> listWithRegionAndCategoryId(@RequestParam String region,
                                 @RequestParam("category") Long categoryId){
        return restaurantService.getRestaurantsWithRegionAndCategoryId(region, categoryId);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable Long id){
        return restaurantService.getRestaurant(id);
    }
}

