package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.CategoryRequestDto;
import com.example.tapchikhcn.dto.response.CategoryResponseDto;
import org.springframework.stereotype.Service;


public interface CategoryService {
    CategoryResponseDto getById(int id);
    CategoryResponseDto createBy(CategoryRequestDto dto);
    CategoryResponseDto updateBy(int id, CategoryRequestDto dto);
    void deleteBy(int id);
}
