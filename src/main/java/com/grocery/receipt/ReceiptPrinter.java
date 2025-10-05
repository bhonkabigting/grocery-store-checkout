package com.grocery.receipt;

import com.grocery.service.CartItem;

import java.util.List;

public class ReceiptPrinter {

    public void printReceipt(List<CartItem> items, double total) {
        System.out.println("===== RECEIPT =====");
        for (CartItem item : items) {
            String qty = item.getProduct().getUnit().contains("kg")
                    ? String.format("%.2f kg", item.getQuantity())
                    : String.format("%.0f pc", item.getQuantity());

            if (item.getPrice() == 0.0) {
                System.out.printf("%-20s %s%n",
                        item.getProduct().getName() + " (" + qty + ")",
                        "(FREE)");
            } else {
                System.out.printf("%-20s x %.2f = %.2f%n",
                        item.getProduct().getName() + " (" + qty + ")",
                        item.getProduct().getUnitPrice(),
                        item.getPrice());
            }
        }
        System.out.println("-------------------");
        System.out.printf("TOTAL: %.2f%n", total);
        System.out.println("===================");
    }
}
