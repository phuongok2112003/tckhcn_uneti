package com.example.tapchikhcn.mapper;

import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserResponseDto entityToDto(UserEntity entity);
}
