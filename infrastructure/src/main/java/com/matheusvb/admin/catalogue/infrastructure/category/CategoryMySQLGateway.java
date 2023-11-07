package com.matheusvb.admin.catalogue.infrastructure.category;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;
import com.matheusvb.admin.catalogue.domain.category.CategorySearchQuery;
import com.matheusvb.admin.catalogue.domain.pagination.Pagination;
import com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryJPAEntity;
import com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    private Category save(final Category aCategory) {
        return this.repository.save(CategoryJPAEntity.from(aCategory)).toAggregate();
    }

    @Override
    public void deleteById(CategoryID anId) {

    }
}
