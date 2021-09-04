package com.example.eatgo.controller;

import com.example.eatgo.domain.Review;
import com.example.eatgo.exception.TokenNotExistException;
import com.example.eatgo.service.ReviewService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        if(authentication == null){
            throw new TokenNotExistException();
        }
        Claims claims = (Claims) authentication.getPrincipal();
        String userName = claims.get("userName", String.class);
        Review review = reviewService.addReview(restaurantId, userName, resource);
        URI uri = new URI("/api/restaurants/" + restaurantId + "/reviews/" + review.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
