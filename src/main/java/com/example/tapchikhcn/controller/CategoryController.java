package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.CategoryRequestDto;
import com.example.tapchikhcn.dto.response.CategoryResponseDto;
import com.example.tapchikhcn.services.CategoryService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public EOResponse<CategoryResponseDto> getById(@PathVariable(value = "id") int id) {
        return EOResponse.build(categoryService.getById(id));
    }

    @PostMapping
    public EOResponse<CategoryResponseDto> createBy(@RequestBody CategoryRequestDto dto) {
        return EOResponse.build(categoryService.createBy(dto));
    }

    @PutMapping("/{id}")
    public EOResponse<CategoryResponseDto> updateBy(@PathVariable(value = "id") int id, @RequestBody CategoryRequestDto dto) {
        return EOResponse.build(categoryService.updateBy(id, dto));
    }

    @DeleteMapping("/{id}")
    public EOResponse<Boolean> deleteBy(@PathVariable(value = "id") int id) {
        categoryService.deleteBy(id);
        return EOResponse.build(true);
    }
}
