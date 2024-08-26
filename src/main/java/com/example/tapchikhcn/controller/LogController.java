package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.constans.enums.LogStatus;
import com.example.tapchikhcn.dto.request.LogRequestDto;
import com.example.tapchikhcn.dto.request.PostRequestDto;
import com.example.tapchikhcn.dto.response.LogResponseDto;
import com.example.tapchikhcn.dto.response.PostResponseDto;
import com.example.tapchikhcn.dto.search.LogSearch;
import com.example.tapchikhcn.dto.search.PostSearch;
import com.example.tapchikhcn.services.LogService;
import com.example.tapchikhcn.utils.EOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/log")
public class LogController {
    private final LogService logService;

    @PutMapping("/{id}")
    public EOResponse<LogResponseDto> updateBy(@PathVariable(value = "id") int id, LogStatus status) {
        return EOResponse.build(logService.updateStatus(id, status));
    }
    @GetMapping("/page")
    public EOResponse<Page<LogResponseDto>> searchBy(LogSearch search) {
        return EOResponse.build(logService.searchBy(search));
    }
}
