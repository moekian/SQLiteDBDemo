package com.mohammadkiani.sqlitedbdemo.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "department")
    private String department;

    @NonNull
    @ColumnInfo(name = "joining_date")
    private String joiningDate;

    @ColumnInfo(name = "salary")
    private double salary;

    public Employee(@NonNull String name, @NonNull String department, @NonNull String joiningDate, double salary) {
        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    @NonNull
    public String getJoiningDate() {
        return joiningDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDepartment(@NonNull String department) {
        this.department = department;
    }

    public void setJoiningDate(@NonNull String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
