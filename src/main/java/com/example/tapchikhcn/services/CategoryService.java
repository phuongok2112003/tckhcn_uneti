package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.CategoryRequestDto;
import com.example.tapchikhcn.dto.response.CategoryResponseDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    CategoryResponseDto getById(int id);
    CategoryResponseDto createBy(CategoryRequestDto dto);
    CategoryResponseDto updateBy(int id, CategoryRequestDto dto);
    Page<CategoryResponseDto> getPage(Pageable pageable);
    List<CategoryResponseDto> getAllPosts();
    void deleteBy(int id);
}
