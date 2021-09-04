package com.example.eatgo.controller;

import com.example.eatgo.domain.Reservation;
import com.example.eatgo.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;


    @Test
    public void reserve() throws Exception {
        // userId: 1004, userName: John
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoiSm9obiJ9.0nwaeM3fpDPvRGc64pyIp-JYNnuigCN9t_5ApVhPClQ";
        given(reservationService.addReservation(any(), any(), any(), any())).willReturn(
                Reservation.builder().id(1L).build()
        );

        mockMvc.perform(post("/api/restaurants/1/reservations")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2021-08-29\",\"time\":\"20:00\",\"partySize\":20}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/restaurants/1/reservations/1"))
                .andExpect(content().string("{}"));

        verify(reservationService).addReservation(eq(1004L), eq("John"), eq(1L), any());
    }

}