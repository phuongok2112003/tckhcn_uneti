package com.example.tapchikhcn.services.mapper;

import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    //@Mapping(target = "user", source = "user", ignore = true)
    //@Mapping(target = "category", source = "category", ignore = true)
    PostResponseDto entityToResponse(PostEntity entity);
    PostEntity requestToEntity(PostRequestDto dto);
}
