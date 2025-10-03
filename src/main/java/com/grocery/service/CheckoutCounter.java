package com.grocery.service;

import com.grocery.product.*;
import com.grocery.promotion.BuyXGetYFreePromotion;

import java.util.*;

public class CheckoutCounter {
    private final List<CartItem> cart = new ArrayList<>();
    private final Map<Product, BuyXGetYFreePromotion> promotions = new HashMap<>();
    private double total = 0.0;

    public void addProduct(PieceProduct product) {
        addProductInternal(product, 1.0);
    }

    public void addProduct(BulkProduct product, double quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        addProductInternal(product, quantity);
    }

    private void addProductInternal(Product product, double quantity) {
        BuyXGetYFreePromotion promotion = promotions.get(product);

        double freeQty = 0.0;

        if (promotion != null) {
            freeQty = promotion.apply(quantity);
        }

        // Add paid items
        if (quantity > 0) {
            double price = product.getUnitPrice() * quantity;
            cart.add(new CartItem(product, quantity, price));

            total += price;
        }

        if (freeQty > 0) {
            cart.add(new CartItem(product, freeQty, 0.0));
        }
    }

    public void addPromotion(BuyXGetYFreePromotion promotion) {
        Product product = promotion.getProduct();
        if (promotions.containsKey(product)) {
            throw new IllegalArgumentException(
                    "Promotion for product '" + product.getName() + "' already exists."
            );
        }
        promotions.put(product, promotion);
    }

    public List<CartItem> getCartItems() {
        return cart;
    }

    public double getTotal() {
        return total;
    }
}
