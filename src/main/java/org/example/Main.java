package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StoreManager storeManager = new StoreManager();
        storeManager.loadStoresFromDatabase();

        // 점포가 DB에서 로드되었는지 확인
        if (storeManager.getStores().isEmpty()) {
            System.out.println("DB에 점포가 등록되어 있지 않습니다.");
        }

        while (true) {
            // 메뉴 표시
            System.out.println("\n=== 점포 관리 시스템 ===");
            System.out.println("1. 새로운 점포 추가");
            System.out.println("2. 모든 점포 정보 확인");
            System.out.println("3. 특정 점포의 매출 확인");
            System.out.println("4. 모든 점포의 총 매출 확인");
            System.out.println("5. 특정 점포의 재고 추가");
            System.out.println("6. 특정 점포의 재고 상태 확인");
            System.out.println("7. 특정 점포에서 재고 삭제");
            System.out.println("8. 점포 삭제");
            System.out.println("9. 종료");
            System.out.print("원하는 작업을 선택하세요 (1-9): ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    // 새로운 점포 추가
                    System.out.print("점포 이름을 입력하세요: ");
                    String storeName = scanner.nextLine();

                    System.out.print("점포 위치를 입력하세요: ");
                    String location = scanner.nextLine();

                    Store newStore = new Store(storeName, location);
                    newStore.saveToDatabase();
                    storeManager.addStore(newStore);
                    break;

                case 2:
                    // 모든 점포 정보 보기
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("등록된 점포가 없습니다.");
                    } else {
                        for (Store store : storeManager.getStores().values()) {
                            System.out.println(store); // toString() 메서드가 자동으로 호출되어 점포 정보를 출력
                        }
                    }
                    break;

                case 3:
                    // 특정 점포의 매출 확인
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("DB에 점포가 등록되어 있지 않습니다.");
                    } else {
                        System.out.print("점포 이름을 입력하세요: ");
                        storeName = scanner.nextLine();

                        if (storeManager.doesStoreExist(storeName)) {
                            double totalSales = storeManager.getStoreSales(storeName);
                            System.out.println("점포 " + storeName + "의 총 매출: " + totalSales + "원");
                        } else {
                            System.out.println("해당 점포를 찾을 수 없습니다.");
                        }
                    }
                    break;

                case 4:
                    // 모든 점포의 총 매출 확인
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("DB에 점포가 등록되어 있지 않습니다.");
                    } else {
                        double totalSalesForAllStores = storeManager.getTotalSales();
                        System.out.println("모든 점포의 총 매출: " + totalSalesForAllStores + "원");
                    }
                    break;

                case 5:
                    // 특정 점포에 재고 추가
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("DB에 점포가 등록되어 있지 않습니다.");
                    } else {
                        System.out.print("점포 이름을 입력하세요: ");
                        storeName = scanner.nextLine();

                        Store store = storeManager.getStoreByName(storeName);
                        if (store != null) {
                            System.out.print("재고 항목 이름을 입력하세요: ");
                            String itemName = scanner.nextLine();

                            System.out.print("재고 수량을 입력하세요: ");
                            int quantity = Integer.parseInt(scanner.nextLine());

                            System.out.print("재고 가격을 입력하세요: ");
                            double price = Double.parseDouble(scanner.nextLine());

                            store.addInventory(itemName, quantity, price);
                            System.out.println("재고가 추가되었습니다.");
                        } else {
                            System.out.println("해당 점포를 찾을 수 없습니다.");
                        }
                    }
                    break;

                case 6:
                    // 특정 점포의 재고 상태 확인
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("DB에 점포가 등록되어 있지 않습니다.");
                    } else {
                        System.out.print("점포 이름을 입력하세요: ");
                        storeName = scanner.nextLine();

                        Store store = storeManager.getStoreByName(storeName);
                        if (store != null) {
                            String inventoryStatus = store.getInventoryStatus();
                            System.out.println("점포 " + storeName + "의 재고 상태:\n" + inventoryStatus);
                        } else {
                            System.out.println("해당 점포를 찾을 수 없습니다.");
                        }
                    }
                    break;
                case 7:
                    // 특정 점포의 재고 삭제
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("DB에 점포가 등록되어 있지 않습니다.");
                    } else {
                        System.out.print("점포 이름을 입력하세요: ");
                        storeName = scanner.nextLine();

                        Store store = storeManager.getStoreByName(storeName);
                        if (store != null) {
                            System.out.print("삭제할 재고 항목 이름을 입력하세요: ");
                            String itemName = scanner.nextLine();

                            store.deleteInventory(itemName);
                        } else {
                            System.out.println("해당 점포를 찾을 수 없습니다.");
                        }
                    }
                    break;
                case 8:
                    // 점포 삭제
                    System.out.print("삭제할 점포 이름을 입력하세요: ");
                    storeName = scanner.nextLine();
                    storeManager.deleteStore(storeName);
                    break;

                case 9:
                    // 종료
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return;

                default:
                    System.out.println("올바른 번호를 선택하세요.");
            }
        }
    }
}