package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface PostService {
    PostResponseDto getById(int id);
    PostResponseDto createBy(PostRequestDto dto);
    PostResponseDto updateBy(int id, PostRequestDto dto);
    Page<PostResponseDto> getPage(Pageable pageable);
    void deleteBy(int id);
}
