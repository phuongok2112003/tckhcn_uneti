package com.example.tapchikhcn.services.mapper;

import com.example.tapchikhcn.dto.request.BannerRequestDto;
import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.entity.BannerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BannerResponseMapper {

    BannerResponseDto entityToResponse(BannerEntity entity);

    BannerEntity requestToEntity(BannerRequestDto dto);

}
