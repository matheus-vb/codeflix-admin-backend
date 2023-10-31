package com.matheusvb.admin.catalogue.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryJPAEntity, String> {
}
