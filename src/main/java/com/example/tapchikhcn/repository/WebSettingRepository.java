package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.entity.WebSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSettingRepository extends JpaRepository<WebSetting, Integer> {
}
