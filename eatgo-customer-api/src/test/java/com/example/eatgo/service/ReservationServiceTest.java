package com.example.eatgo.service;

import com.example.eatgo.domain.Reservation;
import com.example.eatgo.domain.ReservationRepository;
import com.example.eatgo.dto.ReservationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @SpyBean
    private ReservationRepository reservationRepository;

    @Test
    public void addReservation(){
        Long userId = 1004L;
        String userName = "John";
        Long restaurantId = 1L;
        ReservationDto.Request request = ReservationDto.Request.builder()
                .date("2021-08-29")
                .time("20:00")
                .partySize(20)
                .build();
        Reservation reservation = reservationService.addReservation(userId, userName, restaurantId, request);

        verify(reservationRepository).save(any());
        assertThat(reservation.getUserName()).isEqualTo(userName);
    }
}