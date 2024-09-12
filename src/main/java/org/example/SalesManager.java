package org.example;

import java.util.HashMap;
import java.util.Map;

public class SalesManager {
    private Map<String, SalesItem> sales = new HashMap<>();

    // 판매 항목 추가
    public void addSale(SalesItem item) {
        sales.put(item.getName(), item);
    }

    // 판매 항목 검색
    public SalesItem getSaleByName(String name) {
        return sales.get(name);
    }

    // 판매 항목 삭제
    public void removeSaleByName(String name) {
        sales.remove(name);
    }

    // 전체 판매 상태 출력
    public void displaySales() {
        for (SalesItem item : sales.values()) {
            System.out.println(item.getName() + " - 판매 수량: " + item.getQuantitySold() + ", 판매 가격: " + item.getSalePrice());
        }
    }
}