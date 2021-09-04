package com.example.eatgo.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String email) {
        super(email + "is already existed email");
    }
}
