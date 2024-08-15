package com.example.tapchikhcn.specification;

import com.example.tapchikhcn.entity.PostEntity;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<PostEntity> searchTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }
}
