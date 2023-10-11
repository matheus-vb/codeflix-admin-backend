package com.matheusvb.admin.catalogue.application;

import com.matheusvb.admin.catalogue.domain.category.Category;

public class UseCase {
    public Category execute() {
        return new Category();
    }
}