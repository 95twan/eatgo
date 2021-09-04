package com.example.eatgo.service;

import com.example.eatgo.domain.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void list(){
        Long restaurantId = 1L;
        reservationService.getReservations(restaurantId);

        verify(reservationRepository).findAllByRestaurantId(restaurantId);

    }

}