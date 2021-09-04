package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.exception.UserExistedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup(){
        mockUserRepository();
    }

    private void mockUserRepository() {
        given(userRepository.save(any())).will(invocation -> {
            User user = invocation.getArgument(0);
            return User.builder()
                    .id(1L)
                    .name(user.getName())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .level(user.getLevel())
                    .build();
        });
    }

    @Test
    public void registerUser(){
        User user = userService.registerUser(User.builder()
                .name("tester")
                .password("1234")
                .email("tester@example.com")
                .build());

        verify(userRepository).save(any());
        assertThat(user.getId(), is(1L));
    }

    @Test
    public void registerUserWithExistEmail(){
        User mockUser = User.builder()
                .id(1L)
                .name("tester")
                .password("1234")
                .email("tester@example.com")
                .build();
        given(userRepository.findByEmail("tester@example.com")).willReturn(Optional.of(mockUser));

        User user = User.builder()
                .name("tester1")
                .password("123456")
                .email("tester@example.com")
                .build();

        assertThrows(UserExistedException.class, () -> userService.registerUser(user));
        verify(userRepository, never()).save(any());
    }
}