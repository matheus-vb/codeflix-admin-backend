package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;
import com.matheusvb.admin.catalogue.domain.exceptions.DomainException;
import com.matheusvb.admin.catalogue.domain.validation.Error;
import com.matheusvb.admin.catalogue.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var id = CategoryID.from(aCommand.id());
        final var name = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(id)
                .orElseThrow(notFound(id));

        aCategory.update(name, description, isActive);

        final var notification = Notification.create();
        aCategory.validate(notification);

        return notification.hasError() ? API.Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return API.Try(() -> this.categoryGateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private static Supplier<DomainException> notFound(CategoryID id) {
        return () -> DomainException.with(
                new Error("Category with id %s was not found".formatted(id.getValue()))
        );
    }
}
