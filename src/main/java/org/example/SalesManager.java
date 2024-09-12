package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesManager {
    // 판매 물품을 데이터베이스에 추가
    public void addSaleItem(String itemName,double price) {
        String sql = "INSERT INTO sales_items (name, price) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setDouble(2, price);
            preparedStatement.executeUpdate();
            System.out.println("판매 물품이 추가되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 물품 이름으로 판매 물품 조회
    public SalesItem getSaleItemByName(String itemName) {
        String sql = "SELECT * FROM sales_items WHERE name = ?";
        SalesItem item = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, itemName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                item = new SalesItem(itemId,name, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    // 모든 판매 물품 조회
    public List<SalesItem> getAllSalesItems() {
        String sql = "SELECT * FROM sales_items";
        List<SalesItem> items = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                SalesItem item = new SalesItem(itemId, name, price);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // 판매 물품 수정
    public void updateSaleItem(String newName, double newPrice) {
        String sql = "UPDATE sales_items SET name = ?, price = ? WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setDouble(2, newPrice);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("판매 물품이 성공적으로 업데이트되었습니다.");
            } else {
                System.out.println("판매 물품을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 판매 물품 삭제
    public void deleteSaleItem(int itemId) {
        String sql = "DELETE FROM sales_items WHERE item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, itemId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("판매 물품이 삭제되었습니다.");
            } else {
                System.out.println("판매 물품을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}