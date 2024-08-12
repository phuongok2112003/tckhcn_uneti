package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.CategoryRequestDto;
import com.example.tapchikhcn.dto.response.CategoryResponseDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.services.CategoryService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

//    @GetMapping("/page")
//    public EOResponse<Page<CategoryResponseDto>> getPage(Pageable pageable) {
//        return EOResponse.build(categoryService.getPage(pageable));
//    }

    @GetMapping("/page")
    public EOResponse<List<CategoryResponseDto>> getPage() {
        return EOResponse.build(categoryService.getAllPosts());
    }

    @DeleteMapping("/{id}")
    public EOResponse<Boolean> deleteBy(@PathVariable(value = "id") int id) {
        categoryService.deleteBy(id);
        return EOResponse.build(true);
    }
}
