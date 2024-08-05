package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.services.PostService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/by-post/{postId}")
//    public EOResponse<List<FileResponseDto>> getByPostId(@PathVariable(value = "postId") int postId) {
//        return EOResponse.build(fileService.getByPostId(postId));
//    }

    @DeleteMapping("/{id}")
    public EOResponse<Boolean> deleteBy(@PathVariable(value = "id") int id) {
        postService.deleteBy(id);
        return EOResponse.build(true);
    }
}
