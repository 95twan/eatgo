package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.dto.SessionDto;
import com.example.eatgo.exception.EmailNotExistException;
import com.example.eatgo.exception.PasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(SessionDto.Request resource) {
        User user = userRepository.findByEmail(resource.getEmail()).orElseThrow(EmailNotExistException::new);
        if(!passwordEncoder.matches(resource.getPassword(), user.getPassword())){
            throw new PasswordWrongException();
        };
        return user;
    }
}
