package com.example.eatgo.service;

import com.example.eatgo.domain.*;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup(){
        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
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
        given(restaurantRepository.findAllByAddressContaining("Seoul")).willReturn(restaurants);
        given(restaurantRepository.findAllByCategoryId(1L)).willReturn(restaurants);
        given(restaurantRepository.findAllByAddressContainingAndCategoryId("Seoul", 1L)).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    private void mockMenuItemRepository(){
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder().name("Kimchi").build();
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        Review review = Review.builder()
                .name("Jocker")
                .score(3)
                .description("Good")
                .build();

        reviews.add(review);

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(1004L);
        verify(reviewRepository).findAllByRestaurantId(1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        Review review = restaurant.getReviews().get(0);
        assertEquals(1004L, restaurant.getId());
        assertEquals("Kimchi", menuItem.getName());
        assertEquals("Good", review.getDescription());
    }

    @Test
    public void getRestaurantWithNotExisted(){
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurant(404L));
    }

    @Test
    public void getRestaurantsWithoutRequestParam(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);

        verify(restaurantRepository).findAll();
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurantsWithRegion(){
        String region = "Seoul";
        List<Restaurant> restaurants = restaurantService.getRestaurantsWithRegion(region);
        Restaurant restaurant = restaurants.get(0);

        verify(restaurantRepository).findAllByAddressContaining(region);
        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getAddress(), containsString(region));
    }

    @Test
    public void getRestaurantsWithCategoryId(){
        Long categoryId = 1L;
        List<Restaurant> restaurants = restaurantService.getRestaurantsWithCategoryId(categoryId);
        Restaurant restaurant = restaurants.get(0);

        verify(restaurantRepository).findAllByCategoryId(categoryId);
        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getCategoryId(), is(categoryId));
    }

    @Test
    public void getRestaurantsWithRegionAndCategory(){
        String region = "Seoul";
        Long categoryId = 1L;
        List<Restaurant> restaurants = restaurantService.getRestaurantsWithRegionAndCategoryId(region, categoryId);
        Restaurant restaurant = restaurants.get(0);

        verify(restaurantRepository).findAllByAddressContainingAndCategoryId(region, categoryId);
        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getAddress(), containsString(region));
        assertThat(restaurant.getCategoryId(), is(categoryId));
    }
}