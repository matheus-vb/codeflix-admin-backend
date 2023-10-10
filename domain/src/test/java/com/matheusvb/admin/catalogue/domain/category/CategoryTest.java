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

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedErrorMessage = "'name' can not be empty";
        final var expectedErrorCount = 1;

        final String expectedName = " ";
        final var expectedDescription = "Uma categoria de filmes";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage , actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount , actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedErrorMessage = "'name' must have between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final String expectedName = "Ab ";
        final var expectedDescription = "Uma categoria de filmes";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage , actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount , actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedErrorMessage = "'name' must have between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final String expectedName = """
                Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o desenvolvimento contínuo de distintas 
                formas de atuação deve passar por modificações independentemente das posturas dos órgãos dirigentes com relação
                 às suas atribuições. O que temos que ter sempre em mente é que a contínua expansão de nossa atividade agrega 
                 valor ao estabelecimento dos métodos utilizados na avaliação de resultados. No entanto, não podemos esquecer 
                 que o surgimento do comércio virtual auxilia a preparação e a composição do processo de comunicação como um 
                 todo. Percebemos, cada vez mais, que a valorização de fatores subjetivos garante a contribuição de um 
                 grupo importante na determinação dos procedimentos normalmente adotados.
                """;

        final var expectedDescription = "Uma categoria de filmes";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage , actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount , actualException.getErrors().size());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
        final String expectedName = "Abc";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);


        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

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
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldNotReceiveError() {
        final String expectedName = "Abc";
        final var expectedDescription = "Uma categoria de filmes";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);


        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getaName());
        Assertions.assertEquals(expectedDescription, actualCategory.getaDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getaCreationDate());
        Assertions.assertNotNull(actualCategory.getaUpdateDate());
        Assertions.assertNotNull(actualCategory.getaDeletionDate());
    }
}
