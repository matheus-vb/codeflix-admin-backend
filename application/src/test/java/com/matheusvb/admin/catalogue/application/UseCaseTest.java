package com.matheusvb.admin.catalogue.application;

import com.matheusvb.admin.catalogue.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCaseTest {
    @Test
    public void testCreateUseCase() {
        UseCase useCase = new UseCase();
        Assertions.assertNotNull(useCase);

        Category category = useCase.execute();
        Assertions.assertNotNull(category);
    }
}
