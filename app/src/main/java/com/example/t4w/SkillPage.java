package com.example.t4w;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SkillPage extends AppCompatActivity{
    RecyclerView list;
    Skills skills;
    Gateway gateway;
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pskill);

        gateway = new Gateway(this);
        database = gateway.getWritableDatabase();

        skills = new Skills(this, gateway, database);
        list = findViewById(R.id.sPage);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(skills);
    }
}
