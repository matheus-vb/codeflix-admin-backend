package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.application.UseCase;
import com.matheusvb.admin.catalogue.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {

}
