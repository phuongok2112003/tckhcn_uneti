package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.BannerRequestDto;
import com.example.tapchikhcn.dto.response.BannerResponseDto;

import java.util.List;


public interface BannerService {
    BannerResponseDto getById(int id);
    BannerResponseDto createBy(BannerRequestDto dto);
    BannerResponseDto updateBy(int id, BannerRequestDto dto);
    List<BannerResponseDto> getByPostId(int postId);
    void deleteBy(int id);
}
