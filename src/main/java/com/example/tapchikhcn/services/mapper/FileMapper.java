package com.example.tapchikhcn.services.mapper;

import com.example.tapchikhcn.dto.request.FileRequestDto;
import com.example.tapchikhcn.dto.response.FileResponseDto;
import com.example.tapchikhcn.entity.FileEntity;
import org.apache.tomcat.jni.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface FileMapper {
    FileResponseDto entityToResponse(FileEntity entity);
    FileEntity requestToEntity(FileRequestDto dto);
}
