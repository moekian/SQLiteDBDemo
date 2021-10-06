package com.mohammadkiani.sqlitedbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mohammadkiani.sqlitedbdemo.room.Employee;
import com.mohammadkiani.sqlitedbdemo.room.EmployeeRoomDB;
import com.mohammadkiani.sqlitedbdemo.util.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    public static final String DATABASE_NAME = "employee_db";
    // 1st approach
    // declare an instance of SQLite DB
//    SQLiteDatabase sqLiteDatabase;

    // 2nd approach using openHelper class
//    DBHelper dbHelper;

    // 3rd approach - room db
    private EmployeeRoomDB employeeRoomDB;

    // views
    private EditText etName, etSalary;
    private Spinner spinnerDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etSalary = findViewById(R.id.et_salary);
        spinnerDept = findViewById(R.id.spinner_dept);

        findViewById(R.id.btn_add_employee).setOnClickListener(this);
        findViewById(R.id.tv_display_employee).setOnClickListener(this);

        // initialize the SQLite db
        /*sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        // create table
        createTable();*/

        // instantiate dbHelper
//        dbHelper = new DBHelper(this);

        // Room db
        employeeRoomDB = EmployeeRoomDB.getInstance(this);
    }

    /*private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(20) NOT NULL, " +
                "department VARCHAR(20) NOT NULL, " +
                "joining_date DATETIME NOT NULL, " +
                "salary DOUBLE NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_employee:
                // add Employee to DB
                addEmployee();
                break;
            case R.id.tv_display_employee:
                // navigate to another activity to see a list of employees
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }
    }

    private void addEmployee() {
        String name = etName.getText().toString().trim();
        String salary = etSalary.getText().toString().trim();
        String department = spinnerDept.getSelectedItem().toString();

        // get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        String joiningDate = sdf.format(calendar.getTime());

        if (name.isEmpty()) {
            etName.setError("name field is mandatory");
            etName.requestFocus();
            return;
        }
        if (salary.isEmpty()) {
            etSalary.setError("salary field is mandatory");
            etSalary.requestFocus();
            return;
        }

        // first approach
        /*String sql = "INSERT INTO employee (name, department, joining_date, salary) " +
                "VALUES (?, ?, ?, ?);";
        sqLiteDatabase.execSQL(sql, new String[]{name, department, joiningDate,salary});*/

        // second approach - open helper class
        // insert method from dbHelper instance
        /*if (dbHelper.addEmployee(name, department, joiningDate, Double.parseDouble(salary)))
            Toast.makeText(this, "Employee (" + name + ") is added to db", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Employee (" + name + ") is not added to db", Toast.LENGTH_SHORT).show();*/

        // third approach - room db
        Employee employee = new Employee(name, department, joiningDate, Double.parseDouble(salary));
        employeeRoomDB.employeeDao().insertEmployee(employee);
        Toast.makeText(this, "Employee (" + name + ") is added to db", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clearFields();
    }

    private void clearFields() {
        etName.setText("");
        etSalary.setText("");
        spinnerDept.setSelection(0);
        etName.clearFocus();
        etSalary.clearFocus();
    }
}











