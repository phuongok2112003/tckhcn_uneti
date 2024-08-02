package com.example.tapchikhcn.rest;

import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.services.BannerService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banner")
@AllArgsConstructor
public class RestBannerController {
    private final BannerService bannerService;

    public EOResponse<BannerResponseDto> getById(@PathVariable(value = "id") int id) {
        return EOResponse.build(bannerService.getById(id));
    }
}
