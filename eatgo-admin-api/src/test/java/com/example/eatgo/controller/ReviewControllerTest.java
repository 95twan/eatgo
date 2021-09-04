package com.example.eatgo.controller;

import com.example.eatgo.domain.Review;
import com.example.eatgo.service.ReviewService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @BeforeEach
    public void setup(){
        mockReviewService();
    }

    private void mockReviewService() {
        List<Review> reviews = new ArrayList<>();
        Review review = Review.builder().name("Jocker").score(3).description("Good").build();
        reviews.add(review);

        given(reviewService.getReviews()).willReturn(reviews);
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Good")));
    }
}