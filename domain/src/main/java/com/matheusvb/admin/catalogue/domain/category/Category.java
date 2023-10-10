package com.matheusvb.admin.catalogue.domain.category;

import com.matheusvb.admin.catalogue.domain.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryID> {
    final CategoryID anId;
    private String aName;
    private String aDescription;
    private boolean isActive;
    private Instant aCreationDate;
    private Instant aUpdateDate;
    private Instant aDeletionDate;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeletionDate
    ) {
        super(anId);
        this.anId = anId;
        this.aName = aName;
        this.aDescription = aDescription;
        this.isActive = isActive;
        this.aCreationDate = aCreationDate;
        this.aUpdateDate = aUpdateDate;
        this.aDeletionDate = aDeletionDate;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive){
        final var id = CategoryID.unique();
        final var now = Instant.now();

        return new Category(id, aName, aDescription, isActive, now, now, null);
    }

    public CategoryID getId() {
        return anId;
    }

    public String getaName() {
        return aName;
    }

    public String getaDescription() {
        return aDescription;
    }

    public boolean isActive() {
        return isActive;
    }

    public Instant getaCreationDate() {
        return aCreationDate;
    }

    public Instant getaUpdateDate() {
        return aUpdateDate;
    }

    public Instant getaDeletionDate() {
        return aDeletionDate;
    }
}