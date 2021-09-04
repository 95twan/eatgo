package com.example.eatgo.advice;

import com.example.eatgo.exception.EmailNotExistException;
import com.example.eatgo.exception.PasswordWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionErrorAdvice {
    @ExceptionHandler(PasswordWrongException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePasswordWrong(){
        return "{}";
    }

    @ExceptionHandler(EmailNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmailNotExist(){
        return "{}";
    }
}
