package com.matheusvb.admin.catalogue.infrastructure.category.persistence;

import com.matheusvb.admin.catalogue.domain.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryJPAEntity, String> {
    Page<CategoryJPAEntity> findAll(Specification<CategoryJPAEntity> whereClause, Pageable page);
}
