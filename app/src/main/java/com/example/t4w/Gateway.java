package com.example.t4w;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Gateway extends SQLiteAssetHelper{
    private static final String name = "T4WTest.db";
    private static final int version = 1;

    public Gateway(Context context){
        super(context, name, null, version);
    }
}
