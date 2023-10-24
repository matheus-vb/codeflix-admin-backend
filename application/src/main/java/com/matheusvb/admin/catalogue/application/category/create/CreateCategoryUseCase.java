package com.matheusvb.admin.catalogue.application.category.create;

import com.matheusvb.admin.catalogue.application.UseCase;
import com.matheusvb.admin.catalogue.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {

}
