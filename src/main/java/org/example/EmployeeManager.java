package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    // 직원 추가
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, age, hourly_wage, location) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setDouble(3, employee.getHourlyWage());
            preparedStatement.setString(4, employee.getLocation());
            preparedStatement.executeUpdate();
            System.out.println("직원이 추가되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이름으로 직원 조회
    public Employee getEmployeeByName(String name) {
        String sql = "SELECT * FROM employees WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                int age = resultSet.getInt("age");
                double workingHours = resultSet.getDouble("working_hours");
                double hourlyWage = resultSet.getDouble("hourly_wage");
                String location = resultSet.getString("location");

                return new Employee(employeeId, name, age, workingHours, hourlyWage, location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 직원 근무 시간 업데이트 (근무 시간이 누적됨)
    public void updateWorkingHours(String name, double hours) {
        Employee employee = getEmployeeByName(name);
        if (employee != null) {
            double updatedHours = employee.getWorkingHours() + hours;
            String sql = "UPDATE employees SET working_hours = ? WHERE name = ?";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, updatedHours);
                preparedStatement.setString(2, name);
                preparedStatement.executeUpdate();
                System.out.println(name + "의 근무 시간이 업데이트되었습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("직원을 찾을 수 없습니다.");
        }
    }

    // 모든 직원 목록 조회
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double workingHours = resultSet.getDouble("working_hours");
                double hourlyWage = resultSet.getDouble("hourly_wage");
                String location = resultSet.getString("location");

                employees.add(new Employee(employeeId, name, age, workingHours, hourlyWage, location));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // 직원 삭제
    public void deleteEmployeeByName(String name) {
        String sql = "DELETE FROM employees WHERE name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("직원이 삭제되었습니다.");
            } else {
                System.out.println("직원을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}