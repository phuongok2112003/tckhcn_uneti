package com.example.tapchikhcn.repository;

import com.example.tapchikhcn.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer > , JpaSpecificationExecutor<UserEntity> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

    UserEntity findByEmailAndUsername(String email,String username);

}
