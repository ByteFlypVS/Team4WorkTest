package com.example.t4w;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Gateway extends SQLiteAssetHelper {
    private static final String name = "T4WTest.db";
    private static final int version = 1;

    public Gateway(Context context) {
        super(context, name, null, version);
    }

    // Method to get data using a provided database object and a SQL query
    public Cursor getData(SQLiteDatabase database, String query) {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    // Original method without passing SQLiteDatabase
    public Cursor getData(String query) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    // Method to insert a new user into the database
    public boolean insertUser(String name, String phone, String gender, String password) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        contentValues.put("password", password);

        long result = -1;
        try {
            result = database.insertOrThrow("user", null, contentValues);
        } catch (Exception e) {
            Log.e("DB Error", "Error inserting user", e);
        }
        return result != -1; // returns true if insertion is successful
    }

    // Method to update user details
    public boolean updateUser(String id, String name, String phone, String gender, String password) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        contentValues.put("password", password);

        int result = database.update("user", contentValues, "id = ?", new String[] { id });
        return result > 0; // returns true if update is successful
    }

    // Method to check if the username and password are correct
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT * FROM user WHERE name = ? AND password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username, password});

        boolean loginSuccess = cursor.getCount() > 0;
        cursor.close();
        return loginSuccess;
    }

    // Method to log the database schema for the 'user' table
    public void logDatabaseSchema() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("PRAGMA table_info(user);", null);
        if (c != null) {
            while (c.moveToNext()) {
                Log.d("DB Schema", "Column: " + c.getString(1) + ", Type: " + c.getString(2));
            }
            c.close();
        }
    }
}
