package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.entity.UserEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface UserService {

    UserResponseDto getUserDtoByUsername(String username);

    UserEntity getUserByUsername(String username);

    UserResponseDto getInfo();
    void logout(HttpServletRequest request);
    UserResponseDto save(UserRequestDto dto);
    UserResponseDto update(@NonNull int id, @NonNull UserRequestDto dto);
    Boolean delete(int id);
    boolean permanentLock(String username);
}
