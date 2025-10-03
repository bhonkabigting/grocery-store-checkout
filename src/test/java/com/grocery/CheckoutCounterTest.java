package com.grocery;

import com.grocery.product.*;
import com.grocery.promotion.*;
import com.grocery.service.*;
import com.grocery.receipt.ReceiptPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutCounterTest {

    private CheckoutCounter counter;
    private PieceProduct chips;
    private BulkProduct rice;
    private BuyXGetYFreePromotion bogoChips;

    @BeforeEach
    void setup() {
        counter = new CheckoutCounter();
        chips = new PieceProduct("Chips", 35);
        rice = new BulkProduct("Rice", 45);
        bogoChips = new BuyXGetYFreePromotion(chips, 1, 1);
    }

    @Test
    void testAddPieceProductUpdatesTotal() {
        counter.addProduct(chips);
        assertEquals(35, counter.getTotal(), 0.001);
        List<CartItem> items = counter.getCartItems();
        assertEquals(1, items.size());
        assertEquals(chips, items.getFirst().getProduct());
        assertEquals(1, items.getFirst().getQuantity());
        assertEquals(35, items.getFirst().getPrice(), 0.001);
    }

    @Test
    void testAddBulkProductUpdatesTotal() {
        counter.addProduct(rice, 2.5); // 2.5 kg
        assertEquals(45 * 2.5, counter.getTotal(), 0.001);
        List<CartItem> items = counter.getCartItems();
        assertEquals(1, items.size());
        assertEquals(rice, items.getFirst().getProduct());
        assertEquals(2.5, items.getFirst().getQuantity(), 0.001);
        assertEquals(45 * 2.5, items.getFirst().getPrice(), 0.001);
    }

    @Test
    void testAddProductWithPromotion() {
        counter.addPromotion(bogoChips);
        counter.addProduct(chips); // Buy 1, should get 1 free
        assertEquals(35, counter.getTotal(), 0.001);

        List<CartItem> items = counter.getCartItems();
        assertEquals(2, items.size());

        CartItem paid = items.get(0);
        CartItem free = items.get(1);

        assertEquals(chips, paid.getProduct());
        assertEquals(1, paid.getQuantity());
        assertEquals(35, paid.getPrice(), 0.001);

        assertEquals(chips, free.getProduct());
        assertEquals(1, free.getQuantity());
        assertEquals(0, free.getPrice(), 0.001);
    }

    @Test
    void testMultiplePromotionApplications() {
        counter.addPromotion(new BuyXGetYFreePromotion(chips, 2, 1));
        counter.addProduct(chips); // 1 pc
        counter.addProduct(chips); // 2nd pc -> trigger promotion
        counter.addProduct(chips); // 3rd pc -> new cycle
        assertEquals(35 * 3, counter.getTotal(), 0.001);

        List<CartItem> items = counter.getCartItems();
        // There should be 4 items: 3 paid + 1 free
        assertEquals(4, items.size());
        assertEquals(0, items.get(2).getPrice(), 0.001);
    }

    @Test
    void testReceiptPrinting() {
        counter.addPromotion(bogoChips);
        counter.addProduct(chips);
        counter.addProduct(rice, 1.5);

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ReceiptPrinter printer = new ReceiptPrinter();
        printer.printReceipt(counter.getCartItems(), counter.getTotal());

        System.setOut(originalOut);
        String receipt = outContent.toString();

        assertTrue(receipt.contains("Chips"));
        assertTrue(receipt.contains("(FREE)"));
        assertTrue(receipt.contains("Rice"));
        assertTrue(receipt.contains("TOTAL:"));
    }

    @Test
    void testAddingNegativeBulkQuantityThrows() {
        assertThrows(IllegalArgumentException.class, () -> counter.addProduct(rice, -1));
    }

    @Test
    void testDuplicatePromotionThrows() {
        counter.addPromotion(bogoChips);
        assertThrows(IllegalArgumentException.class, () -> counter.addPromotion(bogoChips));
    }
}