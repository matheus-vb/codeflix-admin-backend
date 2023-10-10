package com.matheusvb.admin.catalogue.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateNewCategory() {
        final var expectedName = "Filmes";
        final var exptectedDescription = "Uma categoria de filmes";
        final var exptectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, exptectedDescription, exptectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(exptectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(exptectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }
}
