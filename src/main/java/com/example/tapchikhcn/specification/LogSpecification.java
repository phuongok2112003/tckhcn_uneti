package com.example.tapchikhcn.specification;

import com.example.tapchikhcn.constans.enums.LogStatus;
import com.example.tapchikhcn.entity.LogEntity;
import org.springframework.data.jpa.domain.Specification;

public class LogSpecification {
    public static Specification<LogEntity> searchStatus(LogStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("logStatus"), status.getName());
        };
    }
    public static Specification<LogEntity> searchCat(String cat) {
        return (root, query, criteriaBuilder) -> {
            if (cat == null) {
                return criteriaBuilder.conjunction();
            }
           return criteriaBuilder.like(criteriaBuilder.lower(root.get("category")),"%"+cat+"%");
        };
    }
}
