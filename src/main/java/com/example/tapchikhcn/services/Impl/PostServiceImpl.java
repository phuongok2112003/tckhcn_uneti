package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.constans.ErrorCodes;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.dto.response.UserResponseDto;
import com.example.tapchikhcn.dto.search.UserSearch;
import com.example.tapchikhcn.dto.search.PostSearch;
import com.example.tapchikhcn.entity.CategoryEntity;
import com.example.tapchikhcn.entity.PostEntity;
import com.example.tapchikhcn.entity.UserEntity;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.CategoryRepository;
import com.example.tapchikhcn.repository.PostRepository;
import com.example.tapchikhcn.repository.UserRepository;
import com.example.tapchikhcn.services.PostService;
import com.example.tapchikhcn.specification.PostSpecification;
import com.example.tapchikhcn.utils.PageUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryServiceImpl categoryServiceImpl;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PostResponseDto getById(int id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        return this.entityToResponseMapper(postEntity);
    }

    @Override
    public PostResponseDto createBy(@NonNull PostRequestDto dto) {
        validatePostRequestDto(dto);
        PostEntity postEntity = this.requestToEntityMapper(dto);
        postRepository.save(postEntity);
        return this.entityToResponseMapper(postEntity);
    }

    @Override
    public PostResponseDto updateBy(@NonNull int id,@NonNull PostRequestDto dto) {
        validatePostRequestDto(dto);
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        postEntity = this.requestToEntityMapper(dto);
        postEntity.setId(id);
        postRepository.save(postEntity);
        return this.entityToResponseMapper(postEntity);
    }

    @Override
    public void deleteBy(int id) {
        postRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        postRepository.deleteById(id);
    }


    @Override
    public Page<PostResponseDto> searchBy(PostSearch search) {

        Pageable pageable = PageUtils.getPageableWithSort(search.getPageIndex(), search.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        Specification<PostEntity> spec = Specification.where(null);

        if (search.getTitle() != null && !search.getTitle().isEmpty()) {
            spec = spec.and(PostSpecification.searchTitle(search.getTitle()));
        }

        Page<PostEntity> entityList =  postRepository.findAll(spec, pageable);
        List<PostResponseDto> postResponseDtos = entityList.stream()
                .map(this::entityToResponseMapper)
                .collect(Collectors.toList());

        return new PageImpl<>(postResponseDtos,pageable,entityList.getTotalElements());
    }


    private PostResponseDto entityToResponseMapper(PostEntity entity) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setSummary(entity.getSummary());
        dto.setBody(entity.getBody());
        dto.setView(entity.getView());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus().getName());
        dto.setSelected(entity.isSelected());
        dto.setBreakingNews(entity.isBreakingNews());
        dto.setPublishedAt(entity.getPublishedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAuthorName(entity.getAuthorName());
        dto.setFile(entity.getFile());
        if (entity.getUser() != null) {
            dto.setUser(entity.getUser().getId());
        }
        if (entity.getUser() != null) {
            dto.setCategory(entity.getCategory().getId());
        }
        return dto;
    }

    private PostEntity requestToEntityMapper(PostRequestDto dto) {
        PostEntity entity = new PostEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setSummary(dto.getSummary());
        entity.setBody(dto.getBody());
        entity.setView(dto.getView());
        entity.setImage(dto.getImage());
        entity.setStatus(dto.getStatus());
        entity.setSelected(dto.isSelected());
        entity.setBreakingNews(dto.isBreakingNews());
        entity.setPublishedAt(dto.getPublishedAt());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setAuthorName(dto.getAuthorName());
        entity.setFile(dto.getFile());

        if (dto.getUser() != 0) {
            UserEntity userEntity = userRepository.findById(dto.getUser())
                    .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(dto.getUser())));
            entity.setUser(userEntity);
        }

        if (dto.getUser() != 0) {
            CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(dto.getCategory())));
            entity.setCategory(categoryEntity);
        }

        return entity;
    }

    private void validatePostRequestDto(@NonNull PostRequestDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new EOException(ErrorCodes.NOT_EMPTY, MessageCodes.NOT_NULL, dto.getTitle());
        }
        if (dto.getSummary() == null || dto.getSummary().trim().isEmpty()) {
            throw new EOException(ErrorCodes.NOT_EMPTY, MessageCodes.NOT_NULL, dto.getSummary());
        }
        if (dto.getBody() == null || dto.getBody().trim().isEmpty()) {
            throw new EOException(ErrorCodes.NOT_EMPTY, MessageCodes.NOT_NULL, dto.getTitle());
        }
        if (dto.getAuthorName() == null || dto.getAuthorName().trim().isEmpty()) {
            throw new EOException(ErrorCodes.NOT_EMPTY, MessageCodes.NOT_NULL, dto.getAuthorName());
        }
    }
}
