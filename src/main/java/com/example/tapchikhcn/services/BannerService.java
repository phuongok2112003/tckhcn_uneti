package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.BannerRequestDto;
import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.entity.BannerEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BannerService {
//    BannerResponseDto createBy(@NonNull BannerRequestDto dto);
    BannerResponseDto getById(int id);
//    BannerResponseDto updateBy(@NonNull int id, @NonNull BannerRequestDto banner);
//    void deleteBy(@NonNull int id);
}
