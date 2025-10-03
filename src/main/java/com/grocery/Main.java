package com.grocery;

import com.grocery.product.*;
import com.grocery.promotion.*;
import com.grocery.service.*;
import com.grocery.receipt.ReceiptPrinter;

public class Main {
    public static void main(String[] args) {
        // Create products
        PieceProduct chips = new PieceProduct("Chips", 35);
        BulkProduct rice = new BulkProduct("Rice", 45);

        // Create checkout counter
        CheckoutCounter counter = new CheckoutCounter();

        // Add promotions
        BuyXGetYFreePromotion bogoChips = new BuyXGetYFreePromotion(chips, 1, 1); // Buy 1 get 1 free
        counter.addPromotion(bogoChips);

        // Simulate scanning products
        counter.addProduct(chips);       // Scan 1 Chips
        counter.addProduct(chips);       // Scan 2nd Chips (trigger free)
        counter.addProduct(rice, 2.0);   // Scan 2 kg Rice


        // Print formatted receipt
        ReceiptPrinter printer = new ReceiptPrinter();
        printer.printReceipt(counter.getCartItems(), counter.getTotal());
    }
}
