package com.example.eatgo.service;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import com.example.eatgo.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User resource) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(resource.getName());
        user.setEmail(resource.getEmail());
        user.setLevel(resource.getLevel());

        return user;
    }

    @Transactional
    public User deactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.deactivate();
        return user;
    }
}
