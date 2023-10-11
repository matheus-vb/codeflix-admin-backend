package com.matheusvb.admin.catalogue.application;

import com.matheusvb.admin.catalogue.domain.category.Category;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN anIn);
}