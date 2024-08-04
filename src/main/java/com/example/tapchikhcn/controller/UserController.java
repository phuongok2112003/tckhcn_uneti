package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.dto.search.EntiySearch;
import com.example.tapchikhcn.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

  @PostAuthorize("returnObject.username == authentication.name or hasAuthority('admin')")
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
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable int id,@RequestBody @NonNull UserRequestDto userDto) {

        return userService.update(id,userDto);
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/search")
    public Page<UserResponseDto> searh(EntiySearch search) {

        return userService.searchBy(search);
    }


}