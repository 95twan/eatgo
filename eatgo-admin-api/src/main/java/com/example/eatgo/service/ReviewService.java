package com.example.eatgo.service;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

}
