package com.grocery.product;

    public class BulkProduct extends Product {
        private final double pricePerKg;

        public BulkProduct(String name, double pricePerKg) {
            super(name);
            if (pricePerKg <= 0) throw new IllegalArgumentException("Price must be > 0");
            this.pricePerKg = pricePerKg;
        }

        @Override
        public double getUnitPrice() {
            return pricePerKg;
        }
    }
