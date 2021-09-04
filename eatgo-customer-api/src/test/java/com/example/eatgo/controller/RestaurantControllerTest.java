package com.example.eatgo.controller;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.domain.Review;
import com.example.eatgo.exception.RestaurantNotFoundException;
import com.example.eatgo.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private RestaurantService restaurantService;

   @BeforeEach
   public void setup(){
      MockRestaurantRepository();
   }

   public void MockRestaurantRepository(){
      List<Restaurant> restaurants = new ArrayList<>();

      Restaurant restaurant = Restaurant.builder()
              .id(1004L)
              .categoryId(1L)
              .name("Bob zip")
              .address("Seoul")
              .build();

      MenuItem menuItem = MenuItem.builder().name("Kimchi").build();
      restaurant.addMenuItem(menuItem);
      Review review = Review.builder().name("Jocker").score(3).description("Good").build();
      restaurant.addReview(review);

      restaurants.add(restaurant);

      given(restaurantService.getRestaurants()).willReturn(restaurants);
      given(restaurantService.getRestaurantsWithRegion("Seoul")).willReturn(restaurants);
      given(restaurantService.getRestaurantsWithCategoryId(1L)).willReturn(restaurants);
      given(restaurantService.getRestaurantsWithRegionAndCategoryId("Seoul", 1L)).willReturn(restaurants);
      given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);
      given(restaurantService.getRestaurant(444L)).willThrow(new RestaurantNotFoundException(404L));
   }

   @Test
   public void listWithoutRequestParam() throws Exception {
      mockMvc.perform(get("/api/restaurants"))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().string(containsString("\"id\":1004")))
              .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));

      verify(restaurantService).getRestaurants();
   }

   @Test
    public void listWithRegion() throws Exception {
      String region = "Seoul";
       mockMvc.perform(get("/api/restaurants?region=" + region))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("\"id\":1004")))
               .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
               .andExpect(content().string(containsString("\"address\":\"" + region + "\"")));

       verify(restaurantService).getRestaurantsWithRegion(region);
   }

   @Test
   public void listWithCategoryId() throws Exception {
      Long categoryId = 1L;
      mockMvc.perform(get("/api/restaurants?category=" + categoryId))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().string(containsString("\"id\":1004")))
              .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
              .andExpect(content().string(containsString("\"categoryId\":" + categoryId)));

      verify(restaurantService).getRestaurantsWithCategoryId(categoryId);
   }

   @Test
   public void listWithRegionAndCategory() throws Exception {
      String region = "Seoul";
      Long categoryId = 1L;
      mockMvc.perform(get("/api/restaurants?region=" + region + "&category=" + categoryId))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().string(containsString("\"id\":1004")))
              .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
              .andExpect(content().string(containsString("\"address\":\"" + region + "\"")))
              .andExpect(content().string(containsString("\"categoryId\":" + categoryId)));

      verify(restaurantService).getRestaurantsWithRegionAndCategoryId(region, categoryId);
   }

   @Test
   public void detailWithExisted() throws Exception {
      mockMvc.perform(get("/api/restaurants/1004"))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().string(containsString("\"id\":1004")))
              .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
              .andExpect(content().string(containsString("Kimchi")))
              .andExpect(content().string(containsString("Good")));

   }

   @Test
   public void detailWithNotExisted() throws Exception {
      mockMvc.perform(get("/api/restaurants/444"))
              .andDo(print())
              .andExpect(status().isNotFound())
              .andExpect(content().string("{}"));
   }
}