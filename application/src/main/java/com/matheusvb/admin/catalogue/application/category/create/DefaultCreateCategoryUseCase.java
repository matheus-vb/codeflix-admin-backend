package com.matheusvb.admin.catalogue.application.category.create;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.validation.handler.Notification;
import com.matheusvb.admin.catalogue.domain.validation.handler.ThrowsValidationHandler;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        String name = aCommand.name();
        String description = aCommand.description();
        boolean active = aCommand.isActive();

        final var notification = Notification.create();

        final var category = Category.newCategory(name, description, active);
        category.validate(notification);

        if (notification.hasError()) {
        }

        final var createdCategory = categoryGateway.create(category);

        return CreateCategoryOutput.from(createdCategory);
    }
}
