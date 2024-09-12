package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StoreManager storeManager = new StoreManager();
        EmployeeManager employeeManager = new EmployeeManager();
        SalesManager salesManager = new SalesManager();
        storeManager.loadStoresFromDatabase();

        // 점포가 DB에서 로드되었는지 확인
        if (storeManager.getStores().isEmpty()) {
            System.out.println("DB에 점포가 등록되어 있지 않습니다.");
        }

        while (true) {
            // 메뉴 표시
            System.out.println("\n=== 관리 시스템 ===");
            System.out.println("1. 새로운 점포 추가");
            System.out.println("2. 모든 점포 정보 확인");
            System.out.println("3. 특정 점포의 매출 확인");
            System.out.println("4. 모든 점포의 총 매출 확인");
            System.out.println("5. 특정 점포의 재고 추가");
            System.out.println("6. 특정 점포의 재고 상태 확인");
            System.out.println("7. 특정 점포에서 재고 수정");
            System.out.println("8. 특정 점포에서 재고 삭제");
            System.out.println("9. 점포 삭제");
            System.out.println("10. 새로운 직원 추가");
            System.out.println("11. 직원 정보 수정");
            System.out.println("12. 직원 삭제");
            System.out.println("13. 직원 조회");
            System.out.println("14. 모든 직원 조회");
            System.out.println("15. 판매 물품 추가");
            System.out.println("16. 판매 물품 수정");
            System.out.println("17. 모든 판매 물품 조회");
            System.out.println("18. 종료");

            System.out.print("원하는 작업을 선택하세요 (1-18): ");
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
                    // 특정 점포의 재고 수정
                    System.out.print("수정할 점포 이름을 입력하세요: ");
                    storeName = scanner.nextLine();
                    Store store = storeManager.getStoreByName(storeName);

                    if (store != null) {
                        System.out.print("수정할 재고 항목 이름을 입력하세요: ");
                        String itemName = scanner.nextLine();
                        InventoryStatus itemToUpdate = store.getInventoryItem(itemName);
                        if (itemToUpdate != null) {
                            System.out.println("수정할 정보를 선택하세요:");
                            System.out.println("1. 수량 수정");
                            System.out.println("2. 가격 수정");
                            System.out.print("선택: ");
                            int inventoryOption = Integer.parseInt(scanner.nextLine());

                            if (inventoryOption == 1) {
                                System.out.print("새로운 수량을 입력하세요: ");
                                int newQuantity = Integer.parseInt(scanner.nextLine());
                                itemToUpdate.setQuantity(newQuantity);
                                store.updateInventory(itemToUpdate);
                                System.out.println(itemName + "의 수량이 업데이트되었습니다.");
                            } else if (inventoryOption == 2) {
                                System.out.print("새로운 가격을 입력하세요: ");
                                double newPrice = Double.parseDouble(scanner.nextLine());
                                itemToUpdate.setPrice(newPrice);
                                store.updateInventory(itemToUpdate);
                                System.out.println(itemName + "의 가격이 업데이트되었습니다.");
                            } else {
                                System.out.println("잘못된 선택입니다.");
                            }
                        } else {
                            System.out.println("해당 재고 항목을 찾을 수 없습니다.");
                        }
                    } else {
                        System.out.println("해당 점포를 찾을 수 없습니다.");
                    }
                    break;

                case 8:
                    // 특정 점포의 재고 삭제
                    if (storeManager.getStores().isEmpty()) {
                        System.out.println("DB에 점포가 등록되어 있지 않습니다.");
                    } else {
                        System.out.print("점포 이름을 입력하세요: ");
                        storeName = scanner.nextLine();

                        store = storeManager.getStoreByName(storeName);
                        if (store != null) {
                            System.out.print("삭제할 재고 항목 이름을 입력하세요: ");
                            String itemName = scanner.nextLine();

                            store.deleteInventory(itemName);
                        } else {
                            System.out.println("해당 점포를 찾을 수 없습니다.");
                        }
                    }
                    break;

                case 9:
                    // 점포 삭제
                    System.out.print("삭제할 점포 이름을 입력하세요: ");
                    storeName = scanner.nextLine();
                    storeManager.deleteStore(storeName);
                    break;

                case 10:
                    // 새로운 직원 추가
                    System.out.print("직원 이름을 입력하세요: ");
                    String employeeName = scanner.nextLine();

                    System.out.print("직원 나이를 입력하세요: ");
                    int employeeAge = Integer.parseInt(scanner.nextLine());

                    System.out.print("시급을 입력하세요: ");
                    double hourlyWage = Double.parseDouble(scanner.nextLine());

                    System.out.print("근무할 점포 위치를 입력하세요: ");
                    String employeeLocation = scanner.nextLine();

                    Employee newEmployee = new Employee(0, employeeName, employeeAge, 0, hourlyWage, employeeLocation);
                    employeeManager.addEmployee(newEmployee);
                    break;

                case 11:
                    // 직원 정보 수정
                    System.out.print("수정할 직원 이름을 입력하세요: ");
                    employeeName = scanner.nextLine();
                    Employee employeeToUpdate = employeeManager.getEmployeeByName(employeeName);
                    int tmp = 1;

                    if (employeeToUpdate != null) {
                        System.out.println("\n수정할 정보를 선택하세요:");
                        System.out.println("1. 나이");
                        System.out.println("2. 시급");
                        System.out.println("3. 근무할 점포 위치");
                        System.out.println("4. 근무 시간 추가");
                        System.out.print("선택: ");
                        option = Integer.parseInt(scanner.nextLine());

                        switch (option) {
                            case 1:
                                System.out.print("새로운 나이를 입력하세요: ");
                                int newAge = Integer.parseInt(scanner.nextLine());
                                employeeToUpdate.setAge(newAge);
                                System.out.println("나이가 업데이트되었습니다.");
                                break;

                            case 2:
                                System.out.print("새로운 시급을 입력하세요: ");
                                double newHourlyWage = Double.parseDouble(scanner.nextLine());
                                employeeToUpdate.setHourlyWage(newHourlyWage);
                                System.out.println("시급이 업데이트되었습니다.");
                                break;

                            case 3:
                                System.out.print("새로운 근무할 점포 위치를 입력하세요: ");
                                String newLocation = scanner.nextLine();
                                store = storeManager.getStoreByName(newLocation);
                                if (store == null) {
                                    // store가 null인 경우, 즉 해당 위치에 점포가 존재하지 않는 경우
                                    System.out.println("해당 점포가 존재하지 않습니다.");
                                    tmp = 0;
                                    break;
                                }
                                    employeeToUpdate.setLocation(newLocation);
                                    System.out.println("근무할 점포 위치가 업데이트되었습니다.");
                                    break;

                            case 4:
                                System.out.print("추가할 근무 시간을 입력하세요: ");
                                double additionalHours = Double.parseDouble(scanner.nextLine());
                                employeeManager.updateWorkingHours(employeeName, additionalHours); // 근무 시간 업데이트
                                System.out.println("근무 시간이 업데이트되었습니다.");
                                break;

                            default:
                                System.out.println("잘못된 선택입니다.");
                        }
                        if(tmp != 0) {
                            // 변경된 후 직원 정보 출력
                            System.out.println("업데이트된 직원 정보:");
                            employeeToUpdate.displayEmployeeInfo();
                        }
                    } else {
                        System.out.println("해당 직원을 찾을 수 없습니다.");
                    }
                    break;

                case 12:
                    // 직원 삭제
                    System.out.print("삭제할 직원 이름을 입력하세요: ");
                    employeeName = scanner.nextLine();

                    employeeManager.deleteEmployeeByName(employeeName);
                    break;

                case 13:
                    // 직원 조회
                    System.out.print("조회할 직원 이름을 입력하세요: ");
                    employeeName = scanner.nextLine();

                    Employee employee = employeeManager.getEmployeeByName(employeeName);
                    if (employee != null) {
                        employee.displayEmployeeInfo();
                    } else {
                        System.out.println("해당 직원을 찾을 수 없습니다.");
                    }
                    break;

                case 14:
                    // 모든 직원 조회
                    System.out.println("\n모든 직원 목록:");
                    for (Employee emp : employeeManager.getAllEmployees()) {
                        emp.displayEmployeeInfo();
                        System.out.println();
                    }
                    break;

                case 15:
                    // 새로운 판매 물품 추가

                    System.out.print("판매 물품 이름을 입력하세요: ");
                    String itemName = scanner.nextLine();
                    System.out.print("물품 가격을 입력하세요: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    salesManager.addSaleItem(itemName,price);
                    System.out.println("새로운 판매 물품이 추가되었습니다.");
                    break;

                case 16:
                    // 판매 물품 정보 수정
                    System.out.print("수정할 물품 이름을 입력하세요: ");
                    itemName = scanner.nextLine();
                    SalesItem itemToUpdate = salesManager.getSaleItemByName(itemName);

                    if (itemToUpdate != null) {
                        System.out.println("\n수정할 정보를 선택하세요:");
                        System.out.println("1. 이름");
                        System.out.println("2. 가격");
                        System.out.print("선택: ");
                        option = Integer.parseInt(scanner.nextLine());

                        switch (option) {
                            case 1:
                                System.out.print("새로운 이름을 입력하세요: ");
                                String newName = scanner.nextLine();
                                itemToUpdate.setName(newName);
                                salesManager.updateSaleItem(newName,itemToUpdate.getPrice());
                                break;
                            case 2:
                                System.out.print("새로운 가격을 입력하세요: ");
                                double newPrice = Double.parseDouble(scanner.nextLine());
                                salesManager.updateSaleItem(itemToUpdate.getName(), newPrice);
                                break;
                            default:
                                System.out.println("잘못된 선택입니다.");
                        }
                    } else {
                        System.out.println("해당 물품을 찾을 수 없습니다.");
                    }
                    break;

                case 17:
                    // 모든 판매 물품 조회
                    System.out.println("\n모든 판매 물품 목록:");
                    for (SalesItem item : salesManager.getAllSalesItems()) {
                        System.out.println(item.toString());
                        System.out.println();
                    }
                    break;

                case 18:
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