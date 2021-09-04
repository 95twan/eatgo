package com.example.eatgo.controller;

import com.example.eatgo.domain.Reservation;
import com.example.eatgo.dto.ReservationDto;
import com.example.eatgo.exception.TokenNotExistException;
import com.example.eatgo.service.ReservationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<ReservationDto> list(Authentication authentication){
        if(authentication == null){
            throw new TokenNotExistException();
        }
        Claims claims = (Claims) authentication.getPrincipal();
        Long restaurantId = claims.get("restaurantId", Long.class);

        return reservationService.getReservations(restaurantId);
    }

}
