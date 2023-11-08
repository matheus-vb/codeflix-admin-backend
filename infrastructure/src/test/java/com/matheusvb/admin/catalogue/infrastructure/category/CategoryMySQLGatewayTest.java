package com.matheusvb.admin.catalogue.infrastructure.category;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryID;
import com.matheusvb.admin.catalogue.domain.category.CategorySearchQuery;
import com.matheusvb.admin.catalogue.infrastructure.MySQLGatewayTest;
import com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryJPAEntity;
import com.matheusvb.admin.catalogue.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {
    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjection() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(categoryRepository);
    }

    @Test
    public void givenAValidCategory_whenCallCreate_thenShouldReturnNewCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.create(category);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(category.getId().getValue()).get();

        Assertions.assertEquals(category.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenShouldReturnUpdatedCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = true;

        final var category = Category.newCategory("Old movies", null, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJPAEntity.from(category));
        Assertions.assertEquals(1, categoryRepository.count());

        final var actualInvalidEntity = categoryRepository.findById(category.getId().getValue()).get();
        Assertions.assertEquals("Old movies", actualInvalidEntity.getName());
        Assertions.assertNull(actualInvalidEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualInvalidEntity.isActive());

        final var updatedCategory = category.clone().update(expectedName, expectedDescription, expectedIsActive);
        final var actualCategory = categoryGateway.update(updatedCategory);

        Assertions.assertEquals(1, categoryRepository.count());
        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(category.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(category.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(category.getId().getValue()).get();

        Assertions.assertEquals(category.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(category.getUpdatedAt().isBefore(actualEntity.getUpdatedAt()));
        Assertions.assertEquals(category.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    public void givenAPersistedCategoryAndValidCategoryID_whenTryDeleteIt_thenShouldDeleteCategory() {
        final var category = Category.newCategory("Movies", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJPAEntity.from(category));

        Assertions.assertEquals(1, categoryRepository.count());

        categoryGateway.deleteById(category.getId());

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAnInvalidCategoryID_whenTryDeleteIt_thenShouldDeleteCategory() {
        Assertions.assertEquals(0, categoryRepository.count());

        categoryGateway.deleteById(CategoryID.from("invalidId"));

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAPersistedCategoryAndValidCategoryId_whenCallFindById_thenShouldReturnCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "A movie category";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJPAEntity.from(category));

        Assertions.assertEquals(1, categoryRepository.count());

        final var actualCategory = categoryGateway.findById(category.getId()).get();

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(category.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(category.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(category.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenANotPersistedCategoryAndValidCategoryId_whenCallFindById_thenShouldReturnEmpty() {
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.findById(CategoryID.from("not-stored-id"));

        Assertions.assertEquals(0, categoryRepository.count());

        Assertions.assertTrue(actualCategory.isEmpty());
    }

    @Test
    public void givenPrePersistedCategories_whenCallFindAll_thenShouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var movies = Category.newCategory("Movies", null, true);
        final var docs = Category.newCategory("Documentaries", null, true);
        final var shows = Category.newCategory("Shows", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJPAEntity.from(movies),
                CategoryJPAEntity.from(docs),
                CategoryJPAEntity.from(shows)
        ));

        Assertions.assertEquals(expectedTotal, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(actualResult.total(), expectedTotal);
        Assertions.assertEquals(actualResult.perPage(), expectedPerPage);
        Assertions.assertEquals(actualResult.currentPage(), expectedPage);
        Assertions.assertEquals(actualResult.items().size(), expectedPerPage);

        Assertions.assertEquals(docs.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenEmptyCategoriesTable_whenCallFindAll_thenShouldReturnEmptyPage(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
    }

    @Test
    public void givenFollowPagination_whenCallFindAllWithPage1_thenShouldReturnPaginated(){
        var expectedPage = 1;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var movies = Category.newCategory("Movies", null, true);
        final var docs = Category.newCategory("Documentaries", null, true);
        final var shows = Category.newCategory("Shows", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJPAEntity.from(movies),
                CategoryJPAEntity.from(docs),
                CategoryJPAEntity.from(shows)
        ));

        Assertions.assertEquals(expectedTotal, categoryRepository.count());

        //Page 1
        var query = new CategorySearchQuery(1, 1, "", "name", "asc");
        var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(actualResult.total(), expectedTotal);
        Assertions.assertEquals(actualResult.perPage(), expectedPerPage);
        Assertions.assertEquals(actualResult.currentPage(), expectedPage);
        Assertions.assertEquals(actualResult.items().size(), expectedPerPage);

        Assertions.assertEquals(movies.getId(), actualResult.items().get(0).getId());

        //Page 2
        query = new CategorySearchQuery(2, 1, "", "name", "asc");
        actualResult = categoryGateway.findAll(query);
        expectedPage = 2;

        Assertions.assertEquals(actualResult.total(), expectedTotal);
        Assertions.assertEquals(actualResult.perPage(), expectedPerPage);
        Assertions.assertEquals(actualResult.currentPage(), expectedPage);
        Assertions.assertEquals(actualResult.items().size(), expectedPerPage);

        Assertions.assertEquals(shows.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndDocsAsTerms_whenCallFindAllAndNameMatch_thenShouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var movies = Category.newCategory("Movies", null, true);
        final var docs = Category.newCategory("Documentaries", null, true);
        final var shows = Category.newCategory("Shows", null, true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJPAEntity.from(movies),
                CategoryJPAEntity.from(docs),
                CategoryJPAEntity.from(shows)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "doc", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(actualResult.total(), expectedTotal);
        Assertions.assertEquals(actualResult.perPage(), expectedPerPage);
        Assertions.assertEquals(actualResult.currentPage(), expectedPage);
        Assertions.assertEquals(actualResult.items().size(), expectedPerPage);

        Assertions.assertEquals(docs.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesADescriptionAsTerms_whenCallFindAllAndDescriptionMatch_thenShouldReturnPaginated(){
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var movies = Category.newCategory("Movies", "The best category", true);
        final var docs = Category.newCategory("Documentaries", "The docs category", true);
        final var shows = Category.newCategory("Shows", "Category of tv shows and series", true);

        Assertions.assertEquals(0, categoryRepository.count());

        categoryRepository.saveAll(List.of(
                CategoryJPAEntity.from(movies),
                CategoryJPAEntity.from(docs),
                CategoryJPAEntity.from(shows)
        ));

        Assertions.assertEquals(3, categoryRepository.count());

        final var query = new CategorySearchQuery(0, 1, "BEST", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(actualResult.total(), expectedTotal);
        Assertions.assertEquals(actualResult.perPage(), expectedPerPage);
        Assertions.assertEquals(actualResult.currentPage(), expectedPage);
        Assertions.assertEquals(actualResult.items().size(), expectedPerPage);

        Assertions.assertEquals(movies.getId(), actualResult.items().get(0).getId());
    }
}
