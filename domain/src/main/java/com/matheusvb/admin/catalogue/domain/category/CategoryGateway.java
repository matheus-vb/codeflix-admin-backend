package com.matheusvb.admin.catalogue.domain.category;

import com.matheusvb.admin.catalogue.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category aCategory);
    Optional<Category> findById(CategoryID anId);
    Pagination<Category> findAll(CategorySearchQuery aQuery);
    Category update(Category aCategory);
    void deleteById(CategoryID anId);
}
