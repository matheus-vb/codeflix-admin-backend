package com.matheusvb.admin.catalogue.infrastructure.configuration.usecases;

import com.matheusvb.admin.catalogue.application.category.create.CreateCategoryUseCase;
import com.matheusvb.admin.catalogue.application.category.create.DefaultCreateCategoryUseCase;
import com.matheusvb.admin.catalogue.application.category.delete.DefaultDeleteCategoryUseCase;
import com.matheusvb.admin.catalogue.application.category.delete.DeleteCategoryUseCase;
import com.matheusvb.admin.catalogue.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.matheusvb.admin.catalogue.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.matheusvb.admin.catalogue.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.matheusvb.admin.catalogue.application.category.retrieve.list.ListCategoriesUseCase;
import com.matheusvb.admin.catalogue.application.category.update.DefaultUpdateCategoryUseCase;
import com.matheusvb.admin.catalogue.application.category.update.UpdateCategoryUseCase;
import com.matheusvb.admin.catalogue.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCaseyUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }
}
