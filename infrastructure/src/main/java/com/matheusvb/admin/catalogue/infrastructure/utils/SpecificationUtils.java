package com.matheusvb.admin.catalogue.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;

public final class SpecificationUtils {
    private SpecificationUtils() {}

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get(prop)), "%" + term.toUpperCase() + "%");
    }
}
