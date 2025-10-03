package com.grocery.product;

import java.util.Objects;

public abstract class Product {
    protected final String name;

    protected Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double getUnitPrice();
}
