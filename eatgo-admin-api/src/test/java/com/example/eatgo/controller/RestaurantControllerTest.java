package com.example.eatgo.controller;

import com.example.eatgo.domain.Restaurant;
import com.example.eatgo.exception.RestaurantNotFoundException;
import com.example.eatgo.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

      Restaurant restaurant1 = Restaurant.builder()
              .id(1004L)
              .categoryId(1L)
              .name("Bob zip")
              .address("Seoul")
              .build();

      restaurants.add(restaurant1);

      given(restaurantService.getRestaurants()).willReturn(restaurants);
      given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
      given(restaurantService.getRestaurant(444L)).willThrow(new RestaurantNotFoundException(404L));
      given(restaurantService.addRestaurant(any())).will(invocation -> {
         Restaurant restaurant = invocation.getArgument(0);
         return Restaurant.builder()
                 .id(1234L)
                 .categoryId(restaurant.getCategoryId())
                 .name(restaurant.getName())
                 .address(restaurant.getAddress())
                 .build();
      });
   }

   @Test
    public void list() throws Exception {
       mockMvc.perform(get("/api/restaurants"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("\"id\":1004")))
               .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
   }

   @Test
   public void detailWithExisted() throws Exception {
      mockMvc.perform(get("/api/restaurants/1004"))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().string(containsString("\"id\":1004")))
              .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
   }

   @Test
   public void detailWithNotExisted() throws Exception {
      mockMvc.perform(get("/api/restaurants/444"))
              .andDo(print())
              .andExpect(status().isNotFound())
              .andExpect(content().string("{}"));
   }

   @Test
   public void createWithValidData() throws Exception {
      mockMvc.perform(post("/api/restaurants")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"name\":\"Beryong\", \"categoryId\":1, \"address\":\"Busan\"}"))
              .andExpect(status().isCreated())
              .andExpect(header().string("location", "/api/restaurants/1234"))
              .andExpect(content().string("{}"));

      verify(restaurantService).addRestaurant(any());
   }

   @Test
   public void createWithInvalidData() throws Exception {
         mockMvc.perform(post("/api/restaurants")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content("{\"name\":\"\", \"address\":\"\"}"))
                 .andDo(print())
                 .andExpect(status().isBadRequest());
   }

   @Test
   public void updateWithValidData() throws Exception {
      mockMvc.perform(patch("/api/restaurants/1004")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"name\":\"JOKER Bar\", \"categoryId\":1, \"address\":\"Busan\"}"))
              .andExpect(status().isOk());
      verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
   }

   @Test
   public void updateWithInvalidData() throws Exception {
      mockMvc.perform(patch("/api/restaurants/1004")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\"name\":\"JOKER \", \"address\":\"\"}"))
              .andExpect(status().isBadRequest());
   }
}