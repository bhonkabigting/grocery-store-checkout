package com.grocery.product;

import java.util.Objects;

public abstract class Product {
    protected final String name;
    protected final String unit;

    protected Product(String name, String unit) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public abstract double getUnitPrice();
}
