package com.example.eatgo.controller;

import com.example.eatgo.domain.Reservation;
import com.example.eatgo.dto.ReservationDto;
import com.example.eatgo.exception.TokenNotExistException;
import com.example.eatgo.service.ReservationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> reserve(
            Authentication authentication,
            @PathVariable Long restaurantId,
            @RequestBody ReservationDto.Request request
    ) throws URISyntaxException {
        if(authentication == null){
            throw new TokenNotExistException();
        }
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        String userName = claims.get("userName", String.class);

        Reservation reservation = reservationService.addReservation(userId, userName, restaurantId, request);

        URI uri = new URI("/api/restaurants/" + restaurantId + "/reservations/" + reservation.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
