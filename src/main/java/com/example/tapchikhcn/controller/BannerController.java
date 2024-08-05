package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.BannerRequestDto;
import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.services.BannerService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banner")
public class BannerController {
    private final BannerService bannerService;

    @GetMapping("/{id}")
    public EOResponse<BannerResponseDto> getById(@PathVariable(value = "id") int id) {
        return EOResponse.build(bannerService.getById(id));
    }

    @PostMapping
    public EOResponse<BannerResponseDto> createBy(@RequestBody BannerRequestDto dto) {
        return EOResponse.build(bannerService.createBy(dto));
    }

    @PutMapping("/{id}")
    public EOResponse<BannerResponseDto> updateBy(@PathVariable(value = "id") int id, @RequestBody BannerRequestDto dto) {
        return EOResponse.build(bannerService.updateBy(id, dto));
    }

    @DeleteMapping("/{id}")
    public EOResponse<Boolean> deleteBy(@PathVariable(value = "id") int id) {
        bannerService.deleteBy(id);
        return EOResponse.build(true);
    }

}
