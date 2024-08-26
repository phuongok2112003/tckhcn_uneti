package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogRepository extends JpaRepository<LogEntity, Integer> , JpaSpecificationExecutor<LogEntity> {
}
