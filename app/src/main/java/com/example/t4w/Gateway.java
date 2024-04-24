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

    public Cursor getData(SQLiteDatabase database, String query){
        Cursor cursor = null;

        try{
            cursor = database.rawQuery(query, null);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return cursor;
    }

    public int getUserID(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("user", new String[]{"id"}, "name = ?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
            return -1; // Return -1 if user is not found
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

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
            Log.e("Gateway", "Error inserting user", e);
        }
        return result != -1;
    }

    public boolean updateUser(String id, String name, String phone, String gender, String password) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        contentValues.put("password", password);

        int result = database.update("user", contentValues, "id = ?", new String[]{id});
        return result > 0;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM user WHERE name = ? AND password = ?", new String[]{username, password});
            boolean loginSuccess = cursor.getCount() > 0;
            return loginSuccess;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public boolean insertJobExperience(int userID, String jobTitle, String companyName) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uID", userID);
        values.put("name", companyName);
        values.put("desc", jobTitle);
        values.put("pic", "");  // Assuming you handle the picture elsewhere or default it

        long result = database.insert("uHistory", null, values);
        return result != -1;
    }

    public void logDatabaseSchema() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(user);", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Assuming the column names are in the second column
                Log.d("DB Schema", "Column: " + cursor.getString(1) + ", Type: " + cursor.getString(2));
            }
            cursor.close();
        }
    }
    public PersonalInfo.UserInfo fetchUserData(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"name", "gender", "phone"}, "id = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String gender = cursor.getString(1);
            String phone = cursor.getString(2);
            cursor.close();
            return new PersonalInfo.UserInfo(name, gender, phone);
        }
        if (cursor != null) cursor.close();
        return null; // Return null if user is not found
    }
}
