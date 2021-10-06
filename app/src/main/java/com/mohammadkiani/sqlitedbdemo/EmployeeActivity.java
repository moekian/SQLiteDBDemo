package com.mohammadkiani.sqlitedbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.mohammadkiani.sqlitedbdemo.model.EmployeeModel;
import com.mohammadkiani.sqlitedbdemo.room.Employee;
import com.mohammadkiani.sqlitedbdemo.room.EmployeeRoomDB;
import com.mohammadkiani.sqlitedbdemo.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    // SQLite instance
//    SQLiteDatabase sqLiteDatabase;

    // 2nd approach
//    DBHelper dbHelper;

    // 3rd approach - room db
    private EmployeeRoomDB employeeRoomDB;

    List<Employee> employeeList;
//    List<EmployeeModel> employeeList;
    ListView employeeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

//        sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        // 2nd approach - initialization of dbHelper class
//        dbHelper = new DBHelper(this);
        // 3rd approach - room
        employeeRoomDB = EmployeeRoomDB.getInstance(this);

        employeeListView = findViewById(R.id.lv_employees);
        employeeList = new ArrayList<>();

        // load Employees from db
        loadEmployees();
    }

    private void loadEmployees() {
        // writing a query to select every item from db
        /*String sql = "SELECT * FROM employee";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);*/
        /*Cursor cursor = dbHelper.getAllEmployees();
        if (cursor.moveToFirst()) {
            do {
                // create an employee from model
                employeeList.add(new EmployeeModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                        ));
            } while (cursor.moveToNext());
            cursor.close();
        }*/

        // 3rd approach - room
        employeeList = employeeRoomDB.employeeDao().getAllEmployees();

        // create an adapter to display employees
//        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, R.layout.list_layout_employee, employeeList, dbHelper);
//        employeeListView.setAdapter(employeeAdapter);

        // create an adapter to conform to room db
        EmployeeAdapter adapter = new EmployeeAdapter(this, R.layout.list_layout_employee, employeeList);
        employeeListView.setAdapter(adapter);
    }
}












