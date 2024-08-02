package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserResponseDto getInfo() {
        return userService.getInfo();
    }



}