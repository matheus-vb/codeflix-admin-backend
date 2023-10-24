package com.matheusvb.admin.catalogue.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler aHandler);
    ValidationHandler validate(Validation aValidation);
    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        Error error = null;

        if(getErrors() != null && !getErrors().isEmpty()) {
            error = getErrors().get(0);
        }

        return error;
    }

    public interface Validation {
        void validate();
    }
}
