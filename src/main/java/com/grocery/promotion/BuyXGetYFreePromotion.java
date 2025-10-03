package com.grocery.promotion;

import com.grocery.product.Product;
import com.grocery.service.CartItem;

import java.util.List;

public class BuyXGetYFreePromotion {
    private final Product product;
    private final double buyQuantity;
    private final double freeQuantity;
    private double countdown;

    public BuyXGetYFreePromotion(Product product, double buyQuantity, double freeQuantity) {
        if (buyQuantity <= 0 || freeQuantity <= 0) {
            throw new IllegalArgumentException("Buy and free quantities must be > 0");
        }
        this.product = product;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.countdown = buyQuantity;
    }

    public Product getProduct() {
        return product;
    }

    /**
     * Called when a quantity of product is added.
     * Returns how much should be free.
     */
    public double apply(double addedQuantity) {
        double freeGiven = 0.0;
        double remaining = addedQuantity;

        while (remaining > 0) {
            if (countdown > 0) {
                double consume = Math.min(countdown, remaining);
                countdown -= consume;
                remaining -= consume;
            }
            if (countdown == 0) {
                freeGiven += freeQuantity;
                countdown = buyQuantity;
            }
        }
        return freeGiven;
    }
}
