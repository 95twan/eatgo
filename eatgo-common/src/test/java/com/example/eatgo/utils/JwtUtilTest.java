package com.example.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET = "12345678901234567890123456789012";

    private  JwtUtil jwtUtil;

    @BeforeEach
    public void setup(){
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        String token = jwtUtil.createToken(1004L, "John", null);
        System.out.println(token);

        assertThat(token).contains(".");
    }

    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoiSm9obiJ9.0nwaeM3fpDPvRGc64pyIp-JYNnuigCN9t_5ApVhPClQ";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userId", Long.class)).isEqualTo(1004L);
        assertThat(claims.get("userName")).isEqualTo("John");
    }

}