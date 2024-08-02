package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.constans.enums.ErrorCodes;
import com.example.tapchikhcn.constans.enums.MessageCodes;
import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.BannerRepository;
import com.example.tapchikhcn.services.BannerService;
import com.example.tapchikhcn.services.mapper.BannerResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final BannerResponseMapper bannerResponseMapper;

    @Override
    public BannerResponseDto getById(int id) {
        return this.bannerResponseMapper.entityToResponse(
                bannerRepository.findById(id)
                        .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)))
        );
    }

}
