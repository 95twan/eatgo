package com.example.eatgo.controller;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup(){
        mockUserService();
    }

    private void mockUserService() {
        given(userService.registerUser(any())).will(invocation -> {
            User user = invocation.getArgument(0);
            return User.builder()
                    .id(1L)
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
        });
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tester\", \"email\":\"tester@example.com\",\"password\":\"1234\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/users/1"))
                .andExpect(content().string("{}"));

        verify(userService).registerUser(any());
    }


}