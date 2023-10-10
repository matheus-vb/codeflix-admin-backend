package com.matheusvb.admin.catalogue.domain.category;

import com.matheusvb.admin.catalogue.domain.exceptions.DomainException;
import com.matheusvb.admin.catalogue.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateNewCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Uma categoria de filmes";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getaName());
        Assertions.assertEquals(expectedDescription, actualCategory.getaDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getaCreationDate());
        Assertions.assertNotNull(actualCategory.getaUpdateDate());
        Assertions.assertNull(actualCategory.getaDeletionDate());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedErrorMessage = "'name' can not be null";
        final var expectedErrorCount = 1;

        final String expectedName = null;
        final var expectedDescription = "Uma categoria de filmes";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage , actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount , actualException.getErrors().size());
    }
}
