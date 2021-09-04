package com.example.eatgo.service;

import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.domain.RestaurantRepository;
import com.example.eatgo.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setup(){
        mockRestaurantRepository();
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        assertEquals(1004L, restaurant.getId());
    }

    @Test
    public void getRestaurantWithNotExisted(){
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurant(404L));
    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertEquals(1004L, restaurant.getId());
    }

    @Test
    public void addRestaurant(){
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .categoryId(restaurant.getCategoryId())
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        Restaurant restaurant = Restaurant.builder()
                .categoryId(1L)
                .name("Beryong")
                .address("Busan")
                .build();
        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertEquals(1234L, created.getId());
    }

    @Test
    public void updateRestaurantWithExisted(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

        assertEquals("Sool zip", restaurant.getName());
        assertEquals("Busan", restaurant.getAddress());
    }

    @Test
    public void updateRestaurantWithNotExisted(){
        given(restaurantRepository.findById(444L)).willThrow(new RestaurantNotFoundException(444L));
        assertThrows(RestaurantNotFoundException.class,
                () ->restaurantService.updateRestaurant(444L, "Sool zip", "Busan"));
    }
}