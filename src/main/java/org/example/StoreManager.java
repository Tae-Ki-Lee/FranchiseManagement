package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StoreManager {
    private Map<String, Store> stores = new HashMap<>(); // 점포 목록 (이름 기반)

    // DB에서 모든 점포 가져오기
    public void loadStoresFromDatabase() {
        String sql = "SELECT * FROM stores";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int storeId = resultSet.getInt("store_id");
                String storeName = resultSet.getString("store_name");
                String location = resultSet.getString("location");
                double totalSales = resultSet.getDouble("total_sales");

                Store store = new Store(storeId, storeName, location);
                store.addSales(totalSales); // 매출 추가
                stores.put(storeName, store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 점포 이름으로 점포 찾기
    public Store getStoreByName(String storeName) {
        return stores.get(storeName);
    }

    // 점포 이름으로 존재 여부 확인
    public boolean doesStoreExist(String storeName) {
        return stores.containsKey(storeName);
    }

    // 새로운 점포 추가
    public void addStore(Store store) {
        store.saveToDatabase(); // DB에 저장
        stores.put(store.getStoreName(), store);
        System.out.println("새로운 점포가 추가되었습니다. 점포 이름: " + store.getStoreName());
    }

    // 특정 점포의 매출 확인
    public double getStoreSales(String storeName) {
        Store store = stores.get(storeName);
        if (store != null) {
            return store.getTotalSales();
        } else {
            System.out.println("해당 점포를 찾을 수 없습니다.");
            return 0;
        }
    }

    // 전체 점포의 매출 합계 확인
    public double getTotalSales() {
        double total = 0;
        for (Store store : stores.values()) {
            total += store.getTotalSales();
        }
        return total;
    }

    // 점포 목록 반환
    public Map<String, Store> getStores() {
        return stores;
    }

    // 점포 삭제 메서드
    public void deleteStore(String storeName) {
        Store store = stores.get(storeName);
        if (store == null) {
            System.out.println("해당 점포를 찾을 수 없습니다.");
            return;
        }

        // DB에서 점포 및 관련 데이터 삭제
        String deleteStoreSQL = "DELETE FROM stores WHERE store_name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteStoreSQL)) {
            preparedStatement.setString(1, storeName);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                stores.remove(storeName); // 메모리에서 점포 제거
                System.out.println(storeName + " 점포 및 관련 정보가 삭제되었습니다.");
            } else {
                System.out.println("점포 삭제에 실패했습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}