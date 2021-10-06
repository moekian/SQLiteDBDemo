package com.mohammadkiani.sqlitedbdemo.model;

public class EmployeeModel {

    int id;
    String name, department, joiningDate;
    double salary;

    public EmployeeModel(int id, String name, String department, String joiningDate, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public double getSalary() {
        return salary;
    }
}
