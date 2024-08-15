package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.dto.search.PostSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface PostService {
    PostResponseDto getById(int id);
    PostResponseDto createBy(PostRequestDto dto);
    PostResponseDto updateBy(int id, PostRequestDto dto);
    Page<PostResponseDto> searchBy(PostSearch search);
    void deleteBy(int id);
}
