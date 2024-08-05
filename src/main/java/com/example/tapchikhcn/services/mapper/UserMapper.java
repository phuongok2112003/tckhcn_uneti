package com.example.tapchikhcn.services.mapper;

import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto entityToDto(UserEntity entity);
}
