package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.constans.ErrorCodes;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.constans.enums.LogStatus;
import com.example.tapchikhcn.dto.request.LogRequestDto;
import com.example.tapchikhcn.dto.response.LogResponseDto;
import com.example.tapchikhcn.dto.search.LogSearch;
import com.example.tapchikhcn.entity.LogEntity;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.LogRepository;
import com.example.tapchikhcn.services.LogService;
import com.example.tapchikhcn.specification.LogSpecification;
import com.example.tapchikhcn.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    public LogResponseDto updateStatus(int id, LogStatus status) {
        LogEntity logEntity = logRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        logEntity.setStatus(status.getName());
        logRepository.save(logEntity);
        return entityToResponseMapper(logEntity);
    }

    @Override
    public Page<LogResponseDto> searchBy(LogSearch search) {

        Pageable pageable = PageUtils.getPageableWithSort(search.getPageIndex(), search.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        Specification<LogEntity> spec = Specification.where(null);

        if (search.getStatus() != null) {
            spec = spec.and(LogSpecification.searchStatus(search.getStatus()));
        }

        Page<LogEntity> entityList = logRepository.findAll(spec, pageable);
        List<LogResponseDto> postResponseDtos = entityList.stream()
                .map(this::entityToResponseMapper)
                .collect(Collectors.toList());

        return new PageImpl<>(postResponseDtos, pageable, entityList.getTotalElements());
    }

    private LogEntity requestToEntityMapper(LogRequestDto logRequestDto) {
        LogEntity logEntity = new LogEntity();
        logEntity.setId(logRequestDto.getId());
        logEntity.setAuthor(logRequestDto.getAuthor());
        logEntity.setEmail(logRequestDto.getEmail());
        logEntity.setSentDate(logRequestDto.getSentDate());
        logEntity.setLogStatus(logRequestDto.getLogStatus());
        logEntity.setCategory(logRequestDto.getCategory());
        logEntity.setNote(logRequestDto.getNote());
        return logEntity;
    }

    private LogResponseDto entityToResponseMapper(LogEntity logEntity) {
        LogResponseDto logResponseDto = new LogResponseDto();
        logResponseDto.setId(logEntity.getId());
        logResponseDto.setAuthor(logEntity.getAuthor());
        logResponseDto.setEmail(logEntity.getEmail());
        logResponseDto.setSentDate(logEntity.getSentDate());
        logResponseDto.setLogStatus(logEntity.getLogStatus());
        logResponseDto.setCategory(logEntity.getCategory());
        logResponseDto.setNote(logEntity.getNote());
        return logResponseDto;
    }
}
