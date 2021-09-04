package com.example.eatgo.exception;

public class EmailNotExistException extends RuntimeException {

    public EmailNotExistException() {
        super("Email is not Exist");
    }
}
