package com.example.tapchikhcn.services.Impl;

import com.example.tapchikhcn.constans.ErrorCodes;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.FileRequestDto;
import com.example.tapchikhcn.dto.response.FileResponseDto;
import com.example.tapchikhcn.entity.CategoryEntity;
import com.example.tapchikhcn.entity.FileEntity;
import com.example.tapchikhcn.entity.PostEntity;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.repository.CategoryRepository;
import com.example.tapchikhcn.repository.FileRepository;
import com.example.tapchikhcn.repository.PostRepository;
import com.example.tapchikhcn.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public FileResponseDto getById(int id) {
        return this.entityToResponseMapper(
                fileRepository.findById(id)
                        .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)))
        );
    }
    @Override
    public FileResponseDto createBy(FileRequestDto dto) {
        FileEntity fileEntity = this.requestToEntityMapper(dto);
        fileRepository.save(fileEntity);
        return this.entityToResponseMapper(fileEntity);
    }
    @Override
    public FileResponseDto updateBy(int id, FileRequestDto dto) {
        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        fileEntity = this.requestToEntityMapper(dto);
        fileEntity.setId(id);
        fileRepository.save(fileEntity);
        return this.entityToResponseMapper(fileEntity);
    }
    @Override
    public List<FileResponseDto> getByPostId(int postId) {
        return fileRepository.findByPostId(postId).stream()
                .map(this::entityToResponseMapper)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteBy(int id) {
        fileRepository.findById(id)
                .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(id)));
        fileRepository.deleteById(id);
    }
    private FileResponseDto entityToResponseMapper(FileEntity entity) {
        FileResponseDto dto = new FileResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFile(entity.getFile());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getPost() != null) {
            dto.setPost(entity.getPost().getId());
        }

        if (entity.getCatId() != null) {
            dto.setCatId(entity.getCatId().getId());
        }

        return dto;
    }

    private FileEntity requestToEntityMapper(FileRequestDto dto) {
        FileEntity entity = new FileEntity();
        entity.setName(dto.getName());
        entity.setFile(dto.getFile());
        entity.setCreatedAt(dto.getCreatedAt());

        if (dto.getPost() != 0) {
            PostEntity postEntity = postRepository.findById(dto.getPost())
                    .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(dto.getPost())));
            entity.setPost(postEntity);
        }

        if (dto.getCatId() != 0) {
            CategoryEntity categoryEntity = categoryRepository.findById(dto.getCatId())
                    .orElseThrow(() -> new EOException(ErrorCodes.ERROR_CODE, MessageCodes.ENTITY_NOT_FOUND, String.valueOf(dto.getCatId())));
            entity.setCatId(categoryEntity);
        }

        return entity;
    }
}
