package com.grocery.product;

public class PieceProduct extends Product {
    private final double pricePerPiece;

    public PieceProduct(String name, double pricePerPiece) {
        super(name);
        if (pricePerPiece <= 0) throw new IllegalArgumentException("Price must be > 0");
        this.pricePerPiece = pricePerPiece;
    }

    @Override
    public double getUnitPrice() {
        return pricePerPiece;
    }

    @Override
    public String toString() {
        return name + " (1 pc)";
    }
}
