package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from (final Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
