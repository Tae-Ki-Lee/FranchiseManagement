package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Store {
    private int storeId; // 점포 ID
    private String storeName;
    private String location;
    private double totalSales;
    private InventoryManager inventoryManager;

    // Store 클래스 생성자 (ID가 있는 경우)
    public Store(int storeId, String storeName, String location) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.totalSales = 0;
        this.inventoryManager = new InventoryManager(); // InventoryManager 초기화
    }

    // Store 클래스 생성자 (ID가 없는 경우)
    public Store(String storeName, String location) {
        this.storeName = storeName;
        this.location = location;
        this.totalSales = 0;
        this.inventoryManager = new InventoryManager(); // InventoryManager 초기화
        this.storeId = -1; // 데이터베이스에서 자동 생성된 ID를 사용하기 위해 초기값 설정
    }

    // 매출 추가
    public void addSales(double amount) {
        totalSales += amount;
    }

    // 총 매출 반환
    public double getTotalSales() {
        return totalSales;
    }

    // 점포 이름 반환
    public String getStoreName() {
        return storeName;
    }

    // 재고 로드 메서드
    public void loadInventory() {
        inventoryManager.loadInventoryFromDatabase(storeId);
    }

    // 재고 추가 메서드
    public void addInventory(String itemName, int quantity, double price) {
        InventoryStatus item = new InventoryStatus(itemName, quantity, price);
        inventoryManager.addInventoryItem(item, storeId);
    }

    // 재고 상태 출력
    public String getInventoryStatus() {
        return inventoryManager.getInventoryStatus();
    }

    // 데이터베이스에 점포 저장 (새로 추가하거나 업데이트)
    public void saveToDatabase() {
        try (Connection connection = DBConnection.getConnection()) {
            if (storeId == -1) {
                // 새 점포 추가
                String sql = "INSERT INTO stores (store_name, location, total_sales) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, storeName);
                    preparedStatement.setString(2, location);
                    preparedStatement.setDouble(3, totalSales);
                    preparedStatement.executeUpdate();

                    // 자동 생성된 ID 가져오기
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            storeId = generatedKeys.getInt(1);
                        }
                    }
                }
            } else {
                // 점포 업데이트
                String sql = "UPDATE stores SET store_name = ?, location = ?, total_sales = ? WHERE store_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, storeName);
                    preparedStatement.setString(2, location);
                    preparedStatement.setDouble(3, totalSales);
                    preparedStatement.setInt(4, storeId);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 재고 삭제 메서드
    public void deleteInventory(String itemName) {
        inventoryManager.deleteInventoryItem(itemName, storeId);
    }

    // Store ID 반환
    public int getStoreId() {
        return storeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("점포 ID: %d\n", storeId));
        sb.append(String.format("점포 이름: %s\n", storeName));
        sb.append(String.format("위치: %s\n", location));
        sb.append(String.format("총 매출: %.2f원\n", totalSales));
        sb.append("재고 목록:\n");

        if (inventoryManager != null) {
            sb.append(inventoryManager.getInventoryStatus());
        } else {
            sb.append("재고 정보가 없습니다.\n");
        }

        return sb.toString();
    }
}
