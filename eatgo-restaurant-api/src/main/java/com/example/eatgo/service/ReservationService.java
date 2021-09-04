package com.example.eatgo.service;

import com.example.eatgo.domain.Reservation;
import com.example.eatgo.domain.ReservationRepository;
import com.example.eatgo.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDto> getReservations(Long restaurantId) {
        List<Reservation> reservations = reservationRepository.findAllByRestaurantId(restaurantId);

        return reservations.stream().map(ReservationDto::EntityToDto).collect(Collectors.toList());
    }
}
