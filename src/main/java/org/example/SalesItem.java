package org.example;

public class SalesItem {
    private int itemId;
    private String name;
    private double price;

    // 생성자
    public SalesItem(int itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }
    public SalesItem(String name, double price) {
        this.itemId = -1; //itemid를 모를 때
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    // 물품 정보 출력
    @Override
    public String toString() {
        return "ID : " + itemId + ", 이름 : " + name + ", 가격 : " + price;
    }
}