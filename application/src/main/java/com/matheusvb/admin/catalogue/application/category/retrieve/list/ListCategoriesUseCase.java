package com.matheusvb.admin.catalogue.application.category.retrieve.list;

import com.matheusvb.admin.catalogue.application.UseCase;
import com.matheusvb.admin.catalogue.domain.category.CategorySearchQuery;
import com.matheusvb.admin.catalogue.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
