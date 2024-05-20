package com.about.me.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {Exception.class})
    public String handleException(HttpServletRequest request, Exception ex) {
        return "redirect:/error";
    }
}
