package com.mohammadkiani.sqlitedbdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mohammadkiani.sqlitedbdemo.model.EmployeeModel;
import com.mohammadkiani.sqlitedbdemo.room.Employee;
import com.mohammadkiani.sqlitedbdemo.room.EmployeeRoomDB;
import com.mohammadkiani.sqlitedbdemo.util.DBHelper;

import java.util.Arrays;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter {

    Context context;
    int layoutRes;
//    List<EmployeeModel> employeeModelList;
    List<Employee> employeeModelList;
//    SQLiteDatabase sqLiteDatabase;
    // 2nd approach
//    DBHelper dbHelper;

    // 3rd approach - room
    EmployeeRoomDB employeeRoomDB;

    // first approach
    /*public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<EmployeeModel> employeeList, SQLiteDatabase sqLiteDatabase) {
        super(context, resource, employeeList);
        this.context = context;
        this.layoutRes = resource;
        this.employeeModelList = employeeList;
        this.sqLiteDatabase = sqLiteDatabase;
    }*/

    // second approach - DBHelper
    /*public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<EmployeeModel> employeeList, DBHelper db) {
        super(context, resource, employeeList);
        this.context = context;
        this.layoutRes = resource;
        this.employeeModelList = employeeList;
        this.dbHelper = db;
    }*/

    // third approach - Room DB
    public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<Employee> employeeList) {
        super(context, resource, employeeList);
        this.context = context;
        this.layoutRes = resource;
        this.employeeModelList = employeeList;
        this.employeeRoomDB = EmployeeRoomDB.getInstance(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if (v == null) v = inflater.inflate(layoutRes, null);
        TextView nameTV = v.findViewById(R.id.row_name);
        TextView departmentTV = v.findViewById(R.id.row_department);
        TextView salaryTV = v.findViewById(R.id.row_salary);
        TextView dateTV = v.findViewById(R.id.row_joining_date);

//        EmployeeModel employeeModel = employeeModelList.get(position);
        Employee employeeModel = employeeModelList.get(position);
        nameTV.setText(employeeModel.getName());
        departmentTV.setText(employeeModel.getDepartment());
        salaryTV.setText(String.valueOf(employeeModel.getSalary()));
        dateTV.setText(employeeModel.getJoiningDate());

        // update btn
        v.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }

            private void updateEmployee() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.dialog_update_employee, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                EditText nameET = view.findViewById(R.id.et_name);
                EditText salaryET = view.findViewById(R.id.et_salary);
                Spinner departmentSpinner = view.findViewById(R.id.spinner_dept);

                String[] departmentArray = context.getResources().getStringArray(R.array.departments);
                int position = Arrays.asList(departmentArray).indexOf(employeeModel.getDepartment());

                nameET.setText(employeeModel.getName());
                salaryET.setText(String.valueOf(employeeModel.getSalary()));
                departmentSpinner.setSelection(position);

                view.findViewById(R.id.btn_update_employee).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameET.getText().toString().trim();
                        String salary = salaryET.getText().toString().trim();
                        String department = departmentSpinner.getSelectedItem().toString();

                        if (name.isEmpty()) {
                            nameET.setError("name field is mandatory");
                            nameET.requestFocus();
                            return;
                        }
                        if (salary.isEmpty()) {
                            salaryET.setError("salary field is mandatory");
                            salaryET.requestFocus();
                            return;
                        }

                        /*String sql = "UPDATE employee SET name=?, department=?, salary=? WHERE id=?";
                        sqLiteDatabase.execSQL(sql, new String[]{name, department, salary, String.valueOf(employeeModel.getId())});*/

                        // 2nd approach
                        /*if (dbHelper.updateEmployee(employeeModel.getId(), name, department, Double.parseDouble(salary)));
                            loadEmployees();*/

                        // 3rd approach - room
                        employeeRoomDB.employeeDao().updateEmployee(
                                employeeModel.getId(),
                                name,
                                department,
                                Double.parseDouble(salary)
                        );
                        loadEmployees();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // delete btn
        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employeeModel);
            }

            private void deleteEmployee(Employee employeeModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete the employee
                        /*String sql = "DELETE FROM employee WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{employeeModel.getId()});*/
                        /*if (dbHelper.deleteEmployee(employeeModel.getId())) {
                            // load the employees again
                            loadEmployees();
                        }*/
                        // third approach - room
                        employeeRoomDB.employeeDao().deleteEmployee(employeeModel.getId());
                        loadEmployees();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The Employee (" + employeeModel.getName() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return v;
    }

    @Override
    public int getCount() {
        return employeeModelList.size();
    }

    private void loadEmployees() {
        /*String sql = "SELECT * FROM employee";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);*/
        // 2nd approach
        /*Cursor cursor = dbHelper.getAllEmployees();
        employeeModelList.clear();
        if (cursor.moveToFirst()) {
            do {
                // create an employee from model
                employeeModelList.add(new EmployeeModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }*/

        // room
        employeeModelList = employeeRoomDB.employeeDao().getAllEmployees();
        notifyDataSetChanged();
    }
}
