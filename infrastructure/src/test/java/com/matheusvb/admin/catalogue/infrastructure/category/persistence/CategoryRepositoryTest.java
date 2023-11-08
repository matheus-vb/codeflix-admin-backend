package com.matheusvb.admin.catalogue.infrastructure.category.persistence;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAnInvalidName_whenCallSave_shouldReturnError() {
        final var expectedMessage = "not-null property references a null or transient value : com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryJPAEntity.name";
        final var expectedPropertyName = "name";

        final var category = Category.newCategory("Movies", "A movie category", true);

        final var entity = CategoryJPAEntity.from(category);
        entity.setName(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());
        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidCreatedAt_whenCallSave_shouldReturnError() {
        final var expectedMessage = "not-null property references a null or transient value : com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryJPAEntity.createdAt";
        final var expectedPropertyName = "createdAt";

        final var category = Category.newCategory("Movies", "A movie category", true);

        final var entity = CategoryJPAEntity.from(category);
        entity.setCreatedAt(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());
        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAnInvalidUpdatedAt_whenCallSave_shouldReturnError() {
        final var expectedMessage = "not-null property references a null or transient value : com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryJPAEntity.updatedAt";
        final var expectedPropertyName = "updatedAt";

        final var category = Category.newCategory("Movies", "A movie category", true);

        final var entity = CategoryJPAEntity.from(category);
        entity.setUpdatedAt(null);

        final var actualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(entity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, actualException.getCause());
        Assertions.assertEquals(expectedPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedMessage, actualCause.getMessage());
    }
}
