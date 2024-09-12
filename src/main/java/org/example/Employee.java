package org.example;

public class Employee {
    private int employeeId;
    private String name;
    private int age;
    private double workingHours;
    private double hourlyWage;
    private String location;

    public Employee(int employeeId, String name, int age, double workingHours, double hourlyWage, String location) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.workingHours = workingHours;
        this.hourlyWage = hourlyWage;
        this.location = location;
    }

    // 급여 계산
    public double calculateTotalWage() {
        return workingHours * hourlyWage;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public String getLocation() {
        return location;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // 직원 정보 출력
    public void displayEmployeeInfo() {
        System.out.println("직원 ID: " + employeeId);
        System.out.println("직원 이름: " + name);
        System.out.println("나이: " + age);
        System.out.println("근무 시간: " + workingHours + "시간");
        System.out.println("시급: " + hourlyWage + "원");
        System.out.println("총 임금: " + calculateTotalWage() + "원");
        System.out.println("근무 점포: " + location);
    }
}