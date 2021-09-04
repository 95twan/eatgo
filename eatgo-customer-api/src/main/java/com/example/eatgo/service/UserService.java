package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.exception.UserExistedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User resource) {
        userRepository.findByEmail(resource.getEmail()).ifPresent(user -> {
            throw new UserExistedException(user.getEmail());
        });
        String encodedPassword = passwordEncoder.encode(resource.getPassword());
        resource.setPassword(encodedPassword);
        resource.setLevel(1L);
        return userRepository.save(resource);
    }
}
