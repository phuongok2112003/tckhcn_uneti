package com.example.tapchikhcn.specification;

import com.example.tapchikhcn.entity.PostEntity;
import com.example.tapchikhcn.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> searchUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }
}
