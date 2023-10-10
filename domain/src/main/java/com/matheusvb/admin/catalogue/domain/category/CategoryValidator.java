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
        if (this.category.getaName() == null) {
            this.validationHandler().append(new Error("'name' can not be null"));
        }
    }
}
