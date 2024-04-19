package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main extends AppCompatActivity{
    LinearLayout skills, interests, experience, about;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        skills = findViewById(R.id.hSkill);
        interests = findViewById(R.id.hInterest);
        experience = findViewById(R.id.hExp);
        about = findViewById(R.id.hAbout);
        search = findViewById(R.id.hSearch);

        skills.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intSkill = new Intent(Main.this, SkillPage.class);
                startActivity(intSkill);
            }
        });

        interests.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intInterest = new Intent(Main.this, InterestPage.class);
                startActivity(intInterest);
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intSearch = new Intent(Main.this, JobPage.class);
                startActivity(intSearch);
            }
        });
    }
}