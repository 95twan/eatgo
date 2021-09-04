package com.example.eatgo.exception;

public class TokenNotExistException extends RuntimeException {

    public TokenNotExistException() {
        super("Token is not Exist");
    }
}
