package com.example.eatgo.controller;

import com.example.eatgo.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void list() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJ1c2VyTmFtZSI6IuyjvOyduCIsInJlc3RhdXJhbnRJZCI6MX0.ikNCCYjq3Lr7azUk4DmYOQLgAaN_DmiS_vW05_6NNpw";

        mockMvc.perform(get("/api/reservations")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

        verify(reservationService).getReservations(1L);
    }

}