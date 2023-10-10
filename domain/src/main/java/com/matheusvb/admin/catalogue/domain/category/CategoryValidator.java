package com.matheusvb.admin.catalogue.domain.category;

import com.matheusvb.admin.catalogue.domain.validation.Error;
import com.matheusvb.admin.catalogue.domain.validation.ValidationHandler;
import com.matheusvb.admin.catalogue.domain.validation.Validator;

public class CategoryValidator extends Validator {

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
        final var name = this.category.getaName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' can not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' can not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > 255) {
            this.validationHandler().append(new Error("'name' must have between 3 and 255 characters"));
            return;
        }

        if (length < 3) {
            this.validationHandler().append(new Error("'name' must have between 3 and 255 characters"));
        }
    }
}
