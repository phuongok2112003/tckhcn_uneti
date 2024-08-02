package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.entity.WebSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSettingRepository extends JpaRepository<WebSettingEntity, Integer> {
}
