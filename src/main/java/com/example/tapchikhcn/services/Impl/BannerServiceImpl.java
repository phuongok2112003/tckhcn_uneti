package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.constans.ErrorCodes;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.BannerRequestDto;
import com.example.tapchikhcn.dto.response.BannerResponseDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.entity.BannerEntity;
import com.example.tapchikhcn.entity.PostEntity;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.BannerRepository;
import com.example.tapchikhcn.repository.PostRepository;
import com.example.tapchikhcn.services.BannerService;
import com.example.tapchikhcn.services.mapper.BannerMapper;
import com.example.tapchikhcn.services.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final PostRepository postRepository;

    @Override
    public BannerResponseDto getById(int id) {
        return this.entityToResponseMapper(
                bannerRepository.findById(id)
                        .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)))
        );
    }

    @Override
    public BannerResponseDto createBy(BannerRequestDto dto) {
        validateBannerRequestDto(dto);
        BannerEntity bannerEntity = this.requestToEntityMapper(dto);
        bannerRepository.save(bannerEntity);
        return this.entityToResponseMapper(bannerEntity);
    }

    @Override
    public BannerResponseDto updateBy(int id, BannerRequestDto dto) {
        validateBannerRequestDto(dto);
        BannerEntity bannerEntity = bannerRepository.findById(id)
            .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        bannerEntity = this.requestToEntityMapper(dto);
        bannerEntity.setId(id);
        bannerRepository.save(bannerEntity);
        return this.entityToResponseMapper(bannerEntity);
    }

    @Override
    public List<BannerResponseDto> getByPostId(int postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(postId)));

        List<BannerEntity> bannerEntities = bannerRepository.findByPostId(postId);
        return bannerEntities.stream()
                .map(this::entityToResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBy(int id) {
        bannerRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        bannerRepository.deleteById(id);
    }

    private BannerResponseDto entityToResponseMapper(BannerEntity entity) {
        BannerResponseDto responseDto = new BannerResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setImage(entity.getImage());
        responseDto.setUrl(entity.getUrl());
        responseDto.setType(entity.getType());
        responseDto.setCreatedAt(entity.getCreatedAt());
        responseDto.setUpdatedAt(entity.getUpdatedAt());
        responseDto.setPost(entity.getPost() != null ? entity.getPost().getId() : null);
        return responseDto;
    }

    private BannerEntity requestToEntityMapper(BannerRequestDto dto) {
        BannerEntity entity = new BannerEntity();
        entity.setImage(dto.getImage());
        entity.setUrl(dto.getUrl());
        entity.setType(dto.getType());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        if (dto.getPost() != 0) {
            PostEntity postEntity = postRepository.findById(dto.getPost())
                    .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(dto.getPost())));
            entity.setPost(postEntity);
        }
        return entity;
    }

    private void validateBannerRequestDto(BannerRequestDto dto) {
        if (dto.getImage() == null || dto.getImage().trim().isEmpty()) {
            throw new EOException(ErrorCodes.NOT_EMPTY, MessageCodes.NOT_NULL, "Image cannot be null or empty");
        }
    }

//    private BannerResponseDto entityToResponseMapper(BannerEntity entity) {
//        BannerResponseDto bannerResponseDto = bannerMapper.entityToResponse(entity);
//
//        if (entity.getPost() != null) {
//            bannerResponseDto.setPost(entity.getPost().getId());
//        }
//        return bannerResponseDto;
//    }

//    private BannerEntity requestToEntityMapper(BannerRequestDto dto) {
//        BannerEntity bannerEntity = bannerMapper.requestToEntity(dto);
//
//        if (dto.getPost() != 0) {
//            PostEntity postEntity = postRepository.findById(dto.getPost())
//                    .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(dto.getPost())));
//            bannerEntity.setPost(postEntity);
//        }
//        return bannerEntity;
//    }
}
//    @Override
//    List<BannerResponseDto> getByPost(int id) {
//
//    }