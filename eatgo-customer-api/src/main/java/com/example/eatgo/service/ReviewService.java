package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Long restaurantId, String userName, Review review) {
        review.setName(userName);
        review.setRestaurantId(restaurantId);
        return reviewRepository.save(review);
    }
}
