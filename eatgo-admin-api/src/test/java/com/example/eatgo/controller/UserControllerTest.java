package com.example.eatgo.controller;

import com.example.eatgo.domain.User;
import com.example.eatgo.exception.UserNotFoundException;
import com.example.eatgo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .name("테스터")
                .email("tester@example.com")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(users);
        given(userService.addUser(any())).will(invocation -> {
            User user = invocation.getArgument(0);
            return User.builder()
                    .id(1L)
                    .name(user.getName())
                    .email(user.getEmail())
                    .level(user.getLevel())
                    .build();
        });
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("테스터")));

        verify(userService).getUsers();
    }

    @Test
    public void create() throws Exception{
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Admin\",\"email\":\"admin@example.com\",\"level\":3}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/api/users/1"))
                .andExpect(content().string("{}"));

        verify(userService).addUser(any());
    }

    @Test
    public void update() throws Exception{
        mockMvc.perform(patch("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"UpdateAdmin\",\"email\":\"updateadmin@example.com\",\"level\":3}"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(1L), any());
    }

    @Test
    public void deactivate() throws Exception{
        mockMvc.perform(delete("/api/users/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).deactivateUser(1L);
    }
}