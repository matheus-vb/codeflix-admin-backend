package com.matheusvb.admin.catalogue.application.category.retrieve.get;

import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;
import com.matheusvb.admin.catalogue.domain.exceptions.DomainException;
import com.matheusvb.admin.catalogue.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(String id) {
        final var categoryId = CategoryID.from(id);

        return this.categoryGateway.findById(categoryId)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(categoryId));
    }

    private static Supplier<DomainException> notFound(CategoryID id) {
        return () -> DomainException.with(
                new Error("Category with id %s was not found".formatted(id.getValue()))
        );
    }
}
