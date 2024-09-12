package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryStatus> inventory = new HashMap<>();

    // 재고 데이터를 DB에서 로드
    public void loadInventoryFromDatabase(int storeId) {
        String sql = "SELECT * FROM inventory_status WHERE store_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, storeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String itemName = resultSet.getString("item_name");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");

                    InventoryStatus item = new InventoryStatus(itemName, quantity, price);
                    inventory.put(itemName, item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 재고 항목 추가
    public void addInventoryItem(InventoryStatus item, int storeId) {
        inventory.put(item.getName(), item);

        // DB에 추가하는 SQL
        String sql = "INSERT INTO inventory_status (store_id, item_name, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, storeId);
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setDouble(4, item.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 재고 항목 삭제 메서드
    public void deleteInventoryItem(String itemName, int storeId) {
        // Map에서 재고 항목 삭제 시 확인
        if (!inventory.containsKey(itemName)) {
            System.out.println("해당 재고 항목을 찾을 수 없습니다.");
            return;
        }

        // Map에서 재고 항목 삭제
        inventory.remove(itemName);

        // DB에서 재고 항목 삭제
        String sql = "DELETE FROM inventory_status WHERE item_name = ? AND store_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setInt(2, storeId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("재고 항목이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 재고 항목을 DB에서 찾을 수 없었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 전체 재고 상태 반환
    public String getInventoryStatus() {
        StringBuilder sb = new StringBuilder();
        for (InventoryStatus item : inventory.values()) {
            sb.append(String.format("항목: %s, 수량: %d, 가격: %.2f원\n",
                    item.getName(), item.getQuantity(), item.getPrice()));
        }
        return sb.toString();
    }

    public InventoryStatus getInventoryItem(String itemName) {
        return inventory.get(itemName);
    }
}