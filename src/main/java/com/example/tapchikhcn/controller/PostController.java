package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.dto.search.PostSearch;
import com.example.tapchikhcn.services.PostService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public EOResponse<PostResponseDto> getById(@PathVariable(value = "id") int id) {
        return EOResponse.build(postService.getById(id));
    }

    @PostMapping
    public EOResponse<PostResponseDto> createBy(@RequestBody PostRequestDto dto) {
        return EOResponse.build(postService.createBy(dto));
    }

    @PutMapping("/{id}")
    public EOResponse<PostResponseDto> updateBy(@PathVariable(value = "id") int id, @RequestBody PostRequestDto dto) {
        return EOResponse.build(postService.updateBy(id, dto));
    }

    @GetMapping("/page")
    public EOResponse<Page<PostResponseDto>> getPage(PostSearch search) {
        return EOResponse.build(postService.searchBy(search));
    }

    @DeleteMapping("/{id}")
    public EOResponse<Boolean> deleteBy(@PathVariable(value = "id") int id) {
        postService.deleteBy(id);
        return EOResponse.build(true);
    }
}
