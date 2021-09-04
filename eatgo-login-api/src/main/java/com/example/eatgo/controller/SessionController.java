package com.example.eatgo.controller;

import com.example.eatgo.domain.User;
import com.example.eatgo.dto.SessionDto;
import com.example.eatgo.service.UserService;
import com.example.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<SessionDto.Response> create(@RequestBody SessionDto.Request resource) throws URISyntaxException {
        User user = userService.authenticate(resource);
        String accessToken = jwtUtil.createToken(user.getId(), user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null);
        URI uri = new URI("/api/session");
        return ResponseEntity.created(uri).body(
                SessionDto.Response.builder()
                .accessToken(accessToken)
                .build()
        );
    }
}
