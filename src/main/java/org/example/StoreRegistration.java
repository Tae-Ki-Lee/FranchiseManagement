package org.example;

public class StoreRegistration {

    // 점포 등록
    public Store registerNewStore(String storeName, String location) {
        System.out.println("Registering new store: " + storeName + " at " + location);
        Store store = new Store(storeName, location);
        store.saveToDatabase(); // 데이터베이스에 점포 저장
        return store;
    }
}