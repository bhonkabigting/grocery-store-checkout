package com.grocery.service;

import com.grocery.product.Product;

public class CartItem {
    private final Product product;
    private final double quantity;
    private final double price; // price after promotion (0 if free)

    public CartItem(Product product, double quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
