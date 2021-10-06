package com.mohammadkiani.sqlitedbdemo.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert
    void insertEmployee(Employee employee);

    @Query("DELETE FROM employee")
    void deleteAllEmployees();

    @Query("DELETE FROM employee WHERE id = :id")
    int deleteEmployee(int id);

    @Query("UPDATE employee SET name = :name, department = :department, salary = :salary WHERE id = :id")
    int updateEmployee(int id, String name, String department, double salary);

    @Query("SELECT * FROM employee ORDER BY name")
    List<Employee> getAllEmployees();

    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getEmployee(int id);

}
