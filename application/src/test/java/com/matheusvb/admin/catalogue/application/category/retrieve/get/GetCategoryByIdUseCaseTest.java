package com.matheusvb.admin.catalogue.application.category.retrieve.get;

import com.matheusvb.admin.catalogue.application.category.create.DefaultCreateCategoryUseCase;
import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;
import com.matheusvb.admin.catalogue.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallGetCategoryById_thenShouldReturnCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = true;

        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var category = Category.newCategory(expectedCategory.getName(), expectedCategory.getDescription(), expectedCategory.isActive());
        final var expectedId = category.getId();

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        final var actualOutput = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(CategoryOutput.from(expectedCategory).name(), actualOutput.name());
        Assertions.assertEquals(CategoryOutput.from(expectedCategory).description(), actualOutput.description());
        Assertions.assertEquals(CategoryOutput.from(expectedCategory).isActive(), actualOutput.isActive());
    }

    @Test
    public void givenAnInvalidId_whenCallGetCategoryById_thenShouldReturnNotFound() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Category with id %s was not found".formatted(expectedId.getValue());

        when(categoryGateway.findById(expectedId))
            .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = CategoryID.from("123");

        when(categoryGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
