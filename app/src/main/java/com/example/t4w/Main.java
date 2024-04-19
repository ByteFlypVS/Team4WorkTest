package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Main extends AppCompatActivity{
    TextToSpeech textToSpeech;
    Gateway gateway;
    SQLiteDatabase database;

    ImageView uPic;
    TextView uName;
    LinearLayout skills, interests, experience, about;
    ImageButton skillTTS, interestTTS, experienceTTS, aboutTTS;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        uPic = findViewById(R.id.hPic);
        uName = findViewById(R.id.hName);

        skills = findViewById(R.id.hSkill);
        interests = findViewById(R.id.hInterest);
        experience = findViewById(R.id.hExp);
        about = findViewById(R.id.hAbout);

        skillTTS = findViewById(R.id.hSkillTTS);
        interestTTS = findViewById(R.id.hIntTTS);
        experienceTTS = findViewById(R.id.hExpTTS);
        aboutTTS = findViewById(R.id.hAboutNameTTS);
        search = findViewById(R.id.hSearch);

        gateway = new Gateway(this);
        database = gateway.getWritableDatabase();
        getName();

        initializeTextToSpeech();

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

        experience.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String message = "Work In Progress, Nothing To See Here!";

                speakText(message);
                Toast.makeText(Main.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String message = "Work In Progress, Nothing To See Here!";

                speakText(message);
                Toast.makeText(Main.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String desc = search.getText().toString();
                speakText(desc);

                Intent intSearch = new Intent(Main.this, JobPage.class);
                startActivity(intSearch);
            }
        });

        skillTTS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextView text = findViewById(R.id.hSkillName);
                String desc = text.getText().toString();

                speakText(desc);
            }
        });

        interestTTS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView text = findViewById(R.id.hIntName);
                String desc = text.getText().toString();

                speakText(desc);
            }
        });

        experienceTTS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextView text = findViewById(R.id.hExpName);
                String desc = text.getText().toString();

                speakText(desc);
            }
        });

        aboutTTS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextView text = findViewById(R.id.hAboutName);
                String desc = text.getText().toString();

                speakText(desc);
            }
        });
    }

    private void initializeTextToSpeech(){
        if(textToSpeech == null){
            textToSpeech = new TextToSpeech(Main.this, new TextToSpeech.OnInitListener(){
                @Override
                public void onInit(int status){
                    if(status == TextToSpeech.SUCCESS){
                        int result = textToSpeech.setLanguage(Locale.getDefault());

                        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                            Toast.makeText(Main.this, "Language not supported", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Main.this, "Initialization failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void speakText(String text){
        initializeTextToSpeech();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void getName(){
        String query = "SELECT name FROM user WHERE id = 2";
        Cursor cursor = gateway.getData(database, query);

        if(cursor != null && cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                uName.setText("Hello " + name + "!");
            }
            while(cursor.moveToNext());
            cursor.close();
        }
    }
}