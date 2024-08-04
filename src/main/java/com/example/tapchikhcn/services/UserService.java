package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.UserRequestDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.dto.search.EntiySearch;
import com.example.tapchikhcn.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;


public interface UserService {

    UserResponseDto getUserDtoByUsername(String username);

    UserEntity getUserByUsername(String username);

    UserResponseDto getInfo();
    void logout(HttpServletRequest request);
    UserResponseDto save(UserRequestDto dto);
    UserResponseDto update(@NonNull int id, @NonNull UserRequestDto dto);
    Boolean delete(int id);
    boolean permanentLock(String username);
    Page<UserResponseDto> searchBy(EntiySearch search);


}
