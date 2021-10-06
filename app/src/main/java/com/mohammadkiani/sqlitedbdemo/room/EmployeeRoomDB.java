package com.mohammadkiani.sqlitedbdemo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class EmployeeRoomDB extends RoomDatabase {

    private static final String ROOM_DB_NAME = "employee_room_db";

    public abstract EmployeeDao employeeDao();

    private static volatile EmployeeRoomDB INSTANCE;
    public static EmployeeRoomDB getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), EmployeeRoomDB.class, ROOM_DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        return INSTANCE;
    }
}
