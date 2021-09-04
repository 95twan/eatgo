package com.example.eatgo.controller;

import com.example.eatgo.domain.Review;
import com.example.eatgo.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidData() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoiSm9obiJ9.0nwaeM3fpDPvRGc64pyIp-JYNnuigCN9t_5ApVhPClQ";
        given(reviewService.addReview(eq(1L), eq("John"), any())).will(invocation -> {
            Review review = invocation.getArgument(2);
            return Review.builder()
                    .id(1111L)
                    .restaurantId(review.getRestaurantId())
                    .name(review.getName())
                    .score(review.getScore())
                    .description(review.getDescription())
                    .build();
        });

        mockMvc.perform(post("/api/restaurants/1/reviews")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\" : 3, \"description\" : \"Good\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/restaurants/1/reviews/1111"))
                .andExpect(content().string("{}"))
                .andReturn();

        verify(reviewService).addReview(eq(1L), eq("John"), any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"\", \"score\" : 5, \"description\" : \"Good\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(any(), any(), any());
    }
}