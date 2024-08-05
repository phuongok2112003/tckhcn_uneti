package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByPostId(int postId);
}
