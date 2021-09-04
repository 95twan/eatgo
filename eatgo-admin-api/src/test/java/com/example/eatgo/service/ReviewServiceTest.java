package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup(){
        mockReviewReopsitory();
    }

    private void mockReviewReopsitory() {
        List<Review> reviews = new ArrayList<>();
        Review review = Review.builder().name("Jocker").score(3).description("Good").build();
        reviews.add(review);

        given(reviewRepository.findAll()).willReturn(reviews);
    }

    @Test
    public void getReviews(){
        List<Review> reviews = reviewService.getReviews();
        Review review = reviews.get(0);
        assertThat(review.getDescription(), is("Good"));
    }


}