package com.pet.reminder_app.http.handler;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.pet.reminder_app.http.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
