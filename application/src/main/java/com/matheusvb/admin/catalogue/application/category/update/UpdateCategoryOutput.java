package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from (CategoryID id) {
        return new UpdateCategoryOutput(id);
    }
}
