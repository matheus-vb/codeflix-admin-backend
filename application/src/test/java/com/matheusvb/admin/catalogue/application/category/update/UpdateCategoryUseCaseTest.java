package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.application.category.create.CreateCategoryCommand;
import com.matheusvb.admin.catalogue.application.category.create.DefaultCreateCategoryUseCase;
import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;
import com.matheusvb.admin.catalogue.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateCategory_thenShouldReturnCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Name", null, true);

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(expectedId);

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(updatedCategory -> {
                            return Objects.equals(expectedName, updatedCategory.getName())
                                    && Objects.equals(expectedDescription, updatedCategory.getDescription())
                                    && Objects.equals(expectedIsActive, updatedCategory.isActive())
                                    && Objects.nonNull(updatedCategory.getId())
                                    && Objects.nonNull(updatedCategory.getCreatedAt())
                                    && Objects.nonNull(updatedCategory.getUpdatedAt())
                                    && Objects.isNull(updatedCategory.getDeletedAt())
                                    && aCategory.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                                    && updatedCategory.getCreatedAt().isBefore(updatedCategory.getUpdatedAt());
                        }
                ));
    }

    @Test
    public void givenAnInvalidName_whenCallUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Name", null, true);
        final var expectedId = aCategory.getId();

        final String expectedName = null;
        final var expectedDescription = "Some category";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' can not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallUpdateCategory_thenShouldReturnInactiveCategoryId() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory("Name", null, true);

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(expectedId);

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(updatedCategory -> {
                            return Objects.equals(expectedName, updatedCategory.getName())
                                    && Objects.equals(expectedDescription, updatedCategory.getDescription())
                                    && Objects.equals(expectedIsActive, updatedCategory.isActive())
                                    && Objects.nonNull(updatedCategory.getId())
                                    && Objects.nonNull(updatedCategory.getCreatedAt())
                                    && Objects.nonNull(updatedCategory.getUpdatedAt())
                                    && Objects.nonNull(updatedCategory.getDeletedAt())
                                    && aCategory.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                                    && updatedCategory.getCreatedAt().isBefore(updatedCategory.getUpdatedAt());
                        }
                ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldAnException() {
        final var aCategory = Category.newCategory("Name", null, true);
        final var expectedId = aCategory.getId();

        final var expectedName = "Movies";
        final var expectedDescription = "Some category";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway message";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(updatedCategory -> {
                            return Objects.equals(expectedName, updatedCategory.getName())
                                    && Objects.equals(expectedDescription, updatedCategory.getDescription())
                                    && Objects.equals(expectedIsActive, updatedCategory.isActive())
                                    && Objects.nonNull(updatedCategory.getId())
                                    && Objects.nonNull(updatedCategory.getCreatedAt())
                                    && Objects.nonNull(updatedCategory.getUpdatedAt())
                                    && Objects.isNull(updatedCategory.getDeletedAt());
                        }
                ));
    }

    @Test
    public void givenACommandWithAnInvalidID_whenCallUpdateCategory_thenShouldReturnNotFoundException() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = false;

        final var expectedId = "invalidId";
        final var expectedErrorMessage = "Category with id %s was not found".formatted(expectedId);
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

        Mockito.verify(categoryGateway, times(1)).findById(CategoryID.from(expectedId));

        Mockito.verify(categoryGateway, Mockito.times(0)).update(any());
    }
}
