package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.FileRequestDto;
import com.example.tapchikhcn.dto.response.FileResponseDto;

import java.util.List;

public interface FileService {
    FileResponseDto getById(int id);
    FileResponseDto createBy(FileRequestDto dto);
    FileResponseDto updateBy(int id, FileRequestDto dto);
    List<FileResponseDto> getByPostId(int postId);
    void deleteBy(int id);
}
