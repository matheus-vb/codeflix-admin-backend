package com.matheusvb.admin.catalogue.domain.category;

import com.matheusvb.admin.catalogue.domain.validation.Error;
import com.matheusvb.admin.catalogue.domain.validation.ValidationHandler;
import com.matheusvb.admin.catalogue.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int MAX_NAME_LENGTH = 255;
    public static final int MIN_NAME_LENGTH = 3;
    private final Category category;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' can not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' can not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > MAX_NAME_LENGTH) {
            this.validationHandler().append(new Error("'name' must have between 3 and 255 characters"));
            return;
        }

        if (length < MIN_NAME_LENGTH) {
            this.validationHandler().append(new Error("'name' must have between 3 and 255 characters"));
        }
    }
}
