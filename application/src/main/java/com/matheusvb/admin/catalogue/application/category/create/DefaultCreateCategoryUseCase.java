package com.matheusvb.admin.catalogue.application.category.create;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand aCommand) {
        String name = aCommand.name();
        String description = aCommand.description();
        boolean active = aCommand.isActive();

        final var category = Category.newCategory(name, description, active);
        category.validate(new ThrowsValidationHandler());

        final var createdCategory = categoryGateway.create(category);

        return CreateCategoryOutput.from(createdCategory);
    }
}
