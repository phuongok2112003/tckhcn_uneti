package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.TokenDto;
import com.example.tapchikhcn.dto.request.PasswordResetRequest;
import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.dto.search.UserSearch;
import com.example.tapchikhcn.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;


public interface UserService {

    UserResponseDto getUserDtoByUsername(String username);

    UserEntity getUserByUsername(String username);

    UserResponseDto getInfo();
    Boolean logout(HttpServletRequest request);
    UserResponseDto save(UserRequestDto dto);
    UserResponseDto update(@NonNull int id, UserRequestDto dto);
    Boolean delete(int id);
    boolean permanentLock(String username);
    Page<UserResponseDto> searchBy(UserSearch search);

   String sendPasswordResetCode(String email);
   Boolean verifyPasswordResetCode(PasswordResetRequest passwordResetRequest);
    TokenDto refreshToken(String refreshToken);
}
