package com.example.tapchikhcn.services.mapper;

import com.example.tapchikhcn.constans.ErrorCodes;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.BannerRequestDto;
import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.entity.BannerEntity;
import com.example.tapchikhcn.entity.PostEntity;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.PostRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BannerMapper {
//    @Mapping(target = "post", ignore = true)
//    BannerResponseDto entityToResponse(BannerEntity entity);
//
//    @Mapping(target = "post", ignore = true)
//    BannerEntity requestToEntity(BannerRequestDto dto);

}
