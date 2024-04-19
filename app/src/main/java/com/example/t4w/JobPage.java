package com.example.t4w;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class JobPage extends AppCompatActivity{
    RecyclerView list;
    Jobs jobs;
    Gateway gateway;
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pjob);

        gateway = new Gateway(this);
        database = gateway.getWritableDatabase();
        jobs = new Jobs(this, gateway, database);

        list = findViewById(R.id.jPage);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(jobs);
    }
}
