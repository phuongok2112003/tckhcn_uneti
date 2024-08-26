package com.example.tapchikhcn.services;

import com.example.tapchikhcn.constans.enums.LogStatus;
import com.example.tapchikhcn.dto.request.LogRequestDto;
import com.example.tapchikhcn.dto.response.LogResponseDto;
import com.example.tapchikhcn.dto.search.LogSearch;
import org.springframework.data.domain.Page;

public interface LogService {
    LogResponseDto updateStatus(int id, LogStatus status);
    Page<LogResponseDto> searchBy(LogSearch search);
}
