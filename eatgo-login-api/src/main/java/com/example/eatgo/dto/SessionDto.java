package com.example.eatgo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDto {

    @Data
    @Builder
    public static class Request{
        private String email;
        private String password;
    }

    @Data
    @Builder
    public static class Response{
        private String accessToken;
    }
}
