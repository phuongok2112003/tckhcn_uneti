package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.TokenDto;
import com.example.tapchikhcn.dto.request.PasswordResetRequest;
import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.dto.search.UserSearch;
import com.example.tapchikhcn.services.EmailService;
import com.example.tapchikhcn.services.UserService;
import com.example.tapchikhcn.utils.EOResponse;
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
    private final EmailService emailService;
  @PostAuthorize("returnObject.username == authentication.name or hasAuthority('admin')")
    @GetMapping("/{username}")
    public EOResponse<UserResponseDto> getByUsername(@PathVariable("username") String username) {
        return EOResponse.build(userService.getUserDtoByUsername(username));
    }

    @GetMapping
    public  EOResponse<UserResponseDto>  getInfo() {
        return  EOResponse.build(userService.getInfo());
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public  EOResponse<UserResponseDto>  save(@RequestBody UserRequestDto userDto) {
        return  EOResponse.build(userService.save(userDto));
    }
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public EOResponse<Boolean> delete(@PathVariable int id) {
        return  EOResponse.build(userService.delete(id));
    }
    @PutMapping("/logout")
    public EOResponse<Boolean> logout(HttpServletRequest request) {

        return EOResponse.build(userService.logout(request));
    }
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{id}")
    public  EOResponse<UserResponseDto>  update(@PathVariable int id,@RequestBody @NonNull UserRequestDto userDto) {

        return  EOResponse.build(userService.update(id,userDto));
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/search")
    public EOResponse<Page<UserResponseDto>> searh(UserSearch search) {

        return  EOResponse.build(userService.searchBy(search));
    }
    @PostMapping("/forgot")
    public EOResponse<String> forgotPassword(@RequestParam String email) {
        return EOResponse.build(userService.sendPasswordResetCode(email));
    }
    @PostMapping("/resetPasword")
    public EOResponse<Boolean> forgotPassword(@RequestBody PasswordResetRequest request) {
      return   EOResponse.build(userService.verifyPasswordResetCode(request));
    }
    @PutMapping("/refresh-token")
    public TokenDto refreshToken(@RequestParam("token") String token) {
        return userService.refreshToken(token);
    }

}