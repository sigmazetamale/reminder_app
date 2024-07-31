package com.pet.reminder_app.http.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/helloPage")
    public String helloPage(@AuthenticationPrincipal OAuth2User principal) {

        return "Hello," + " " + principal.getAttribute("name");
    }
}