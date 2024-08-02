package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Integer> {
}
