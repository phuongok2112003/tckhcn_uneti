package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/{username}")
    public UserResponseDto getByUsername(@PathVariable("username") String username) {
        return userService.getUserDtoByUsername(username);
    }

    @GetMapping
    public UserResponseDto getInfo() {
        return userService.getInfo();
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public UserResponseDto save(@RequestBody UserRequestDto userDto) {
        return userService.save(userDto);
    }
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable int id) {
        return userService.delete(id);
    }
    @PutMapping("/logout")
    public boolean logout(HttpServletRequest request) {
        userService.logout(request);
        return true;
    }
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable int id,@RequestBody @NonNull UserRequestDto userDto) {

        return userService.update(id,userDto);
    }


}