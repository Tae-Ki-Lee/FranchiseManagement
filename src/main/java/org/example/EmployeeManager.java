package org.example;

import java.util.HashMap;
import java.util.Map;

public class EmployeeManager {
    private Map<Integer, Employee> employees = new HashMap<>();

    // 직원 추가
    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
        System.out.println("직원이 추가되었습니다: " + employee.getName());
    }

    // 특정 직원의 총 급여 계산
    public double calculateTotalWage(int employeeId) {
        Employee employee = employees.get(employeeId);
        if (employee != null) {
            return employee.calculateTotalWage();
        } else {
            System.out.println("해당 직원이 없습니다.");
            return 0;
        }
    }

    // 모든 직원의 총 급여 계산
    public double calculateTotalWages() {
        double totalWages = 0;
        for (Employee employee : employees.values()) {
            totalWages += employee.calculateTotalWage();
        }
        return totalWages;
    }

    // 모든 직원 정보 출력
    public void displayAllEmployees() {
        for (Employee employee : employees.values()) {
            employee.displayEmployeeInfo();
        }
    }
}