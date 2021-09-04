package com.example.eatgo.controller;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(@Valid @RequestBody User resource) throws URISyntaxException {
        User user = userService.registerUser(resource);
        URI uri = new URI("/api/users/" + user.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
