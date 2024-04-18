package com.example.t4w;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Gateway extends SQLiteAssetHelper{
    private static final String name = "T4WTest.db";
    private static final int version = 1;

    public Gateway(Context context){
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
}
