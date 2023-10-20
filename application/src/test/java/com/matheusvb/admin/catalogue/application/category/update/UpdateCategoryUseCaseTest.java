package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.application.category.create.DefaultCreateCategoryUseCase;
import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
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
}
