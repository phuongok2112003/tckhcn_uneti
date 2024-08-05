package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.dto.response.CategoryResponseDto;
import com.example.tapchikhcn.entity.CategoryEntity;
import com.example.tapchikhcn.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public CategoryResponseDto entityToResponseMapper(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());  // Assume CategoryEntity has a name property
        return dto;
    }
}
