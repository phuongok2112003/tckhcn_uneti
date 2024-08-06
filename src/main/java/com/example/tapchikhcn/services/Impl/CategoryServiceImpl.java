package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.constans.ErrorCodes;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.CategoryRequestDto;
import com.example.tapchikhcn.dto.response.CategoryResponseDto;
import com.example.tapchikhcn.entity.CategoryEntity;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.CategoryRepository;
import com.example.tapchikhcn.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponseDto getById(int id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        return this.entityToResponseMapper(entity);
    }

    @Override
    public CategoryResponseDto createBy(CategoryRequestDto dto) {
        CategoryEntity entity = requestDtoToEntity(dto);
        categoryRepository.save(entity);
        return this.entityToResponseMapper(entity);
    }

    @Override
    public CategoryResponseDto updateBy(int id, CategoryRequestDto dto) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        entity = this.requestDtoToEntity(dto);
        entity.setId(id);
        categoryRepository.save(entity);
        return this.entityToResponseMapper(entity);
    }

    @Override
    public void deleteBy(int id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        categoryRepository.delete(entity);
    }

    public CategoryResponseDto entityToResponseMapper(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCodeName(entity.getCodeName());
        dto.setType(entity.getType());
        return dto;
    }

    private CategoryEntity requestDtoToEntity(CategoryRequestDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdateAt());
        entity.setCodeName(dto.getCodeName());
        entity.setType(dto.getType());
        return entity;
    }
}
