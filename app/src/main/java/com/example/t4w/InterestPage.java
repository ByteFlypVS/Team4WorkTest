package com.example.t4w;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InterestPage extends AppCompatActivity{
    RecyclerView list;
    Interests interests;
    Gateway gateway;
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinterest);

        gateway = new Gateway(this);
        database = gateway.getWritableDatabase();
        interests = new Interests(this, gateway, database);

        list = findViewById(R.id.iPage);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(interests);
    }
}
