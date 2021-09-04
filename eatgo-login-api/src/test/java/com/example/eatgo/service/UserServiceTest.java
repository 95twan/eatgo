package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.dto.SessionDto;
import com.example.eatgo.exception.EmailNotExistException;
import com.example.eatgo.exception.PasswordWrongException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    public void authenticateWithValidData(){
        String email = "tester@example.com";
        SessionDto.Request request = SessionDto.Request.builder()
                .email(email)
                .password("1234")
                .build();
        User mockUser = User.builder()
                .id(1L)
                .name("tester")
                .email("tester@example.com")
                .build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate(request);

        verify(userRepository).findByEmail(email);
        assertThat(user.getEmail(), is(email));
    }

    @Test
    public void authenticateWithNotExistedEmail(){
        String email = "xxxx@example.com";
        SessionDto.Request request = SessionDto.Request.builder()
                .email(email)
                .password("1234")
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotExistException.class, () -> userService.authenticate(request));
        verify(userRepository).findByEmail(email);
    }

    @Test
    public void authenticateWithWrongPassword(){
        String email = "tester@example.com";
        SessionDto.Request request = SessionDto.Request.builder()
                .email(email)
                .password("xxxx")
                .build();
        User mockUser = User.builder()
                .id(1L)
                .name("tester")
                .email("tester@example.com")
                .build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThrows(PasswordWrongException.class, () -> userService.authenticate(request));
        verify(userRepository).findByEmail(email);
    }
}