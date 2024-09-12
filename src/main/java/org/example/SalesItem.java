package org.example;

public class SalesItem {
    private String name;
    private int quantitySold;
    private double salePrice;

    public SalesItem(String name, int quantitySold, double salePrice) {
        this.name = name;
        this.quantitySold = quantitySold;
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getSalePrice() {
        return salePrice;
    }
}
