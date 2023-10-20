package com.matheusvb.admin.catalogue.application.category.update;

import com.matheusvb.admin.catalogue.application.category.create.DefaultCreateCategoryUseCase;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateCategory_thenShouldReturnCategory() {
        
    }
}
