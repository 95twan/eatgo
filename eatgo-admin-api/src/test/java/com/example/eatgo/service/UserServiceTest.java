package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    public void setup(){
        mockUserRepository();
    }

    private void mockUserRepository() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .name("테스터")
                .email("test@example.com")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(users);
        given(userRepository.save(any())).will(invocation -> {
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
    public void getUsers(){
        List<User> users = userService.getUsers();
        verify(userRepository).findAll();

        User user = users.get(0);
        assertThat(user.getName(), is("테스터"));
    }

    @Test
    public void addUser(){
        User user = userService.addUser(User.builder()
                .name("Admin")
                .email("admin@example.com")
                .level(3L)
                .build());
        verify(userRepository).save(any());

        assertThat(user.getId(), is(1L));
        assertThat(user.getName(), is("Admin"));
    }

    @Test
    public void updateUser(){
        Long userId = 1L;
        User mockUser = User.builder()
                .id(userId)
                .name("Admin")
                .email("admin@example.com")
                .level(3L)
                .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));

        User user = User.builder()
                .id(userId)
                .name("updatedAdmin")
                .email("admin@example.com")
                .level(3L)
                .build();
        User updatedUser = userService.updateUser(userId, user);
        verify(userRepository).findById(userId);

        assertThat(updatedUser.getName(), is("updatedAdmin"));
    }

    @Test
    public void updateUserWithNotExistUser(){
        Long userId = 9999L;
        given(userRepository.findById(userId)).willThrow(new UserNotFoundException(userId));
        User user = User.builder()
                .name("updatedAdmin")
                .email("admin@example.com")
                .level(3L)
                .build();
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, user));
    }
    
    @Test
    public void deactivateUserWithExistUser(){
        Long userId = 1L;
        User mockUser = User.builder()
                .id(userId)
                .name("Admin")
                .email("admin@example.com")
                .level(3L)
                .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));

        User updatedUser = userService.deactivateUser(userId);
        verify(userRepository).findById(userId);

        assertThat(updatedUser.isAdmin(), is(false));
        assertThat(updatedUser.isActive(), is(false));
    }

    @Test
    public void deactivateUserWithNotExistUser(){
        Long userId = 9999L;
        given(userRepository.findById(userId)).willThrow(new UserNotFoundException(userId));
        assertThrows(UserNotFoundException.class, () -> userService.deactivateUser(userId));
    }
}