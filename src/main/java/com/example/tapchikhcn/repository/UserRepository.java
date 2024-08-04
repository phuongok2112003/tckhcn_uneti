package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.dto.search.EntiySearch;
import com.example.tapchikhcn.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer > {
    UserEntity findByUsername(String username);


}
