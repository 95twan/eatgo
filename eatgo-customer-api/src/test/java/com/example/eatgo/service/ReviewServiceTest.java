package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup(){
        mockReviewRepository();
    }

    private void mockReviewRepository() {
        given(reviewRepository.save(any())).will(invocation -> {
            Review review = invocation.getArgument(0);
            return Review.builder()
                    .id(1L)
                    .name(review.getName())
                    .restaurantId(review.getRestaurantId())
                    .score(review.getScore())
                    .description(review.getDescription())
                    .build();
        });
    }

    @Test
    public void addReview(){
        Review review = reviewService.addReview(
                1L,
                "Jocker",
                Review.builder()
                .score(3)
                .description("Good")
                .build()
        );

        verify(reviewRepository).save(any());
        assertThat(review.getId()).isEqualTo(1L);
        assertThat(review.getName()).isEqualTo("Jocker");
        assertThat(review.getRestaurantId()).isEqualTo(1L);
    }
}