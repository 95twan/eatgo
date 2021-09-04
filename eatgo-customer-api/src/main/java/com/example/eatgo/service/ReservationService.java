package com.example.eatgo.service;

import com.example.eatgo.domain.Reservation;
import com.example.eatgo.domain.ReservationRepository;
import com.example.eatgo.dto.ReservationDto;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation addReservation(Long userId, String userName, Long restaurantId, ReservationDto.Request request) {
        Reservation reservation = Reservation.builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .userName(userName)
                .date(request.getDate())
                .time(request.getTime())
                .partySize(request.getPartySize())
                .build();

        return reservationRepository.save(reservation);
    }


}
