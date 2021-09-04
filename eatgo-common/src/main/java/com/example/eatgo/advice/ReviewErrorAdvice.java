package com.example.eatgo.advice;

import com.example.eatgo.exception.RestaurantNotFoundException;
import com.example.eatgo.exception.TokenNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReviewErrorAdvice {
    @ExceptionHandler(TokenNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTokenNotExist(){
        return "{}";
    }
}
