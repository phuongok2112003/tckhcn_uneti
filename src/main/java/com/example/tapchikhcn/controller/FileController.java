package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.dto.request.FileRequestDto;
import com.example.tapchikhcn.dto.response.FileResponseDto;
import com.example.tapchikhcn.services.FileService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{id}")
    public EOResponse<FileResponseDto> getById(@PathVariable(value = "id") int id) {
        return EOResponse.build(fileService.getById(id));
    }

    @PostMapping
    public EOResponse<FileResponseDto> createBy(@RequestBody FileRequestDto dto) {
        return EOResponse.build(fileService.createBy(dto));
    }

    @PutMapping("/{id}")
    public EOResponse<FileResponseDto> updateBy(@PathVariable(value = "id") int id, @RequestBody FileRequestDto dto) {
        return EOResponse.build(fileService.updateBy(id, dto));
    }

    @GetMapping("/by-post/{postId}")
    public EOResponse<List<FileResponseDto>> getByPostId(@PathVariable(value = "postId") int postId) {
        return EOResponse.build(fileService.getByPostId(postId));
    }

    @DeleteMapping("/{id}")
    public EOResponse<Boolean> deleteBy(@PathVariable(value = "id") int id) {
        fileService.deleteBy(id);
        return EOResponse.build(true);
    }
}
