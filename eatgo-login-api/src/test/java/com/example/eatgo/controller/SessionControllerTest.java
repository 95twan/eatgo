package com.example.eatgo.controller;

import com.example.eatgo.domain.User;
import com.example.eatgo.exception.EmailNotExistException;
import com.example.eatgo.exception.PasswordWrongException;
import com.example.eatgo.service.UserService;
import com.example.eatgo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidData() throws Exception{
        Long id = 1004L;
        String name = "John";
        String email = "tester@example.com";
        String password = "1234";
        given(userService.authenticate(any())).willReturn(User.builder().id(id).name(name).level(1L).build());
        given(jwtUtil.createToken(eq(id), eq(name), any())).willReturn("header.payload.signature");

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\", \"password\":\"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")));

        verify(userService).authenticate(any());
        verify(jwtUtil).createToken(any(), any(), any());
    }

    @Test
    public void createRestaurantOwner() throws Exception{
        Long id = 1004L;
        String name = "John";
        String email = "tester@example.com";
        String password = "1234";
        given(userService.authenticate(any())).willReturn(User.builder()
                .id(id)
                .name(name)
                .restaurantId(123L)
                .level(2L)
                .build());
        given(jwtUtil.createToken(id, name, 123L)).willReturn("header.payload.signature");

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\", \"password\":\"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")));

        verify(userService).authenticate(any());
        verify(jwtUtil).createToken(any(), any(), any());
    }

    @Test
    public void createWithNotExistedEmail() throws Exception{
        String email = "xxxx@example.com";
        String password = "qwer";

        given(userService.authenticate(any())).willThrow(new EmailNotExistException());

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\", \"password\":\"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(any());
    }

    @Test
    public void createWithWrongPassword() throws Exception{
        String email = "tester@example.com";
        String password = "qwer";

        given(userService.authenticate(any())).willThrow(new PasswordWrongException());

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\""+ email + "\", \"password\":\"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(any());
    }
}