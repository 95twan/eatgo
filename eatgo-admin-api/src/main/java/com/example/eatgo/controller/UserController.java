package com.example.eatgo.controller;

import com.example.eatgo.domain.User;
import com.example.eatgo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = "application/json;charset=utf-8")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        User user = userService.addUser(resource);
        URI uri = new URI("/api/users/" + user.getId());
        return ResponseEntity.created(uri).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public String update(@RequestBody User resource, @PathVariable Long userId){
        userService.updateUser(userId, resource);
        return "{}";
    }

    @DeleteMapping("/users/{userId}")
    public String deactivate(@PathVariable Long userId){
        userService.deactivateUser(userId);
        return "{}";
    }
}
