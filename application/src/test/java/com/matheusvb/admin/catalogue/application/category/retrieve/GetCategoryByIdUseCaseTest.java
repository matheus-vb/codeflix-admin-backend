package com.matheusvb.admin.catalogue.application.category.retrieve;

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

        Assertions.assertEquals(CategoryOutput.from(expectedCategory), actualOutput);

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(Mockito.argThat(foundCategory -> {
                            return Objects.equals(expectedName, foundCategory.getName())
                                    && Objects.equals(expectedDescription, foundCategory.getDescription())
                                    && Objects.equals(expectedIsActive, foundCategory.isActive())
                                    && Objects.nonNull(foundCategory.getId())
                                    && Objects.nonNull(foundCategory.getCreatedAt())
                                    && Objects.nonNull(foundCategory.getUpdatedAt())
                                    && Objects.isNull(foundCategory.getDeletedAt());
                        }
                ));
    }

    @Test
    public void givenAnInvalidId_whenCallGetCategoryById_thenShouldReturnNotFound() {
        final var expectedErrorMessage = "";
        final var expectedId = CategoryID.from("123");

        when(categoryGateway.findById(expectedId))
            .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.exectute(expectedId.getValue())
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
