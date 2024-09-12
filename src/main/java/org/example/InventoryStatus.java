package org.example;

public class InventoryStatus {
    private String name;
    private int quantity;
    private double price;

    public InventoryStatus(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void useStock(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
        } else {
            System.out.println("재고 부족!");
        }
    }

    public void addStock(int amount) {
        quantity += amount;
    }

    public boolean needsRestocking() {
        return quantity < 10;
    }
}