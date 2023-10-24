package com.matheusvb.admin.catalogue.application.category.retrieve.list;

import com.matheusvb.admin.catalogue.domain.category.Category;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import com.matheusvb.admin.catalogue.domain.category.CategorySearchQuery;
import com.matheusvb.admin.catalogue.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {
    @InjectMocks
    private DefaultListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidQuery_whenCallListCategories_thenShouldReturnCategories() {
        final var categories = List.of(
                Category.newCategory("Movies", null, true),
                Category.newCategory("Shows", null, true),
                Category.newCategory("Documentaries", null, true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var query = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItemCount = 3;
        final var expectedResult = expectedPagination.map(CategoryListOutput::from);

        when(categoryGateway.findAll(eq(query)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.page());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyCategories() {

    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_thenShouldReturnException() {

    }
}
