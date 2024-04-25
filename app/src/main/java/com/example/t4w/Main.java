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

public class Main extends AppCompatActivity {

    int userId;
    TextToSpeech textToSpeech;
    Gateway gateway;
    SQLiteDatabase database;

    ImageView uPic;
    TextView uName;
    LinearLayout skills, interests, experience, about;
    ImageButton skillTTS, interestTTS, experienceTTS, aboutTTS;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        userId = getIntent().getIntExtra("USER_ID", -1);

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

        initializeTextToSpeech();
        getUserInfo();

        setupListeners();
    }

    private void setupListeners() {
        skills.setOnClickListener(v -> {
            Intent intSkill = new Intent(Main.this, SkillPage.class);
            startActivity(intSkill);
        });

        interests.setOnClickListener(v -> {
            Intent intInterest = new Intent(Main.this, InterestPage.class);
            startActivity(intInterest);
        });

        experience.setOnClickListener(v -> {
            String message = "Work In Progress, Nothing To See Here!";
            speakText(message);
            Toast.makeText(Main.this, message, Toast.LENGTH_SHORT).show();
        });

        about.setOnClickListener(v -> {
            Intent intPersonalInfo = new Intent(Main.this, PersonalInfo.class);
            intPersonalInfo.putExtra("USER_ID", userId);
            startActivity(intPersonalInfo);
        });

        search.setOnClickListener(v -> {
            String desc = search.getText().toString();
            speakText(desc);
            Intent intSearch = new Intent(Main.this, JobPage.class);
            startActivity(intSearch);
        });

        skillTTS.setOnClickListener(v -> {
            TextView text = findViewById(R.id.hSkillName);
            String desc = text.getText().toString();
            speakText(desc);
        });

        interestTTS.setOnClickListener(v -> {
            TextView text = findViewById(R.id.hIntName);
            String desc = text.getText().toString();
            speakText(desc);
        });

        experienceTTS.setOnClickListener(v -> {
            TextView text = findViewById(R.id.hExpName);
            String desc = text.getText().toString();
            speakText(desc);
        });

        aboutTTS.setOnClickListener(v -> {
            TextView text = findViewById(R.id.hAboutName);
            String desc = text.getText().toString();
            speakText(desc);
        });

        ImageButton appliedTTS = findViewById(R.id.hAppliedTTS);
        appliedTTS.setOnClickListener(v -> {
            String message = "Applied";
            speakText(message);
        });

        ImageButton logoutTTS = findViewById(R.id.hLogoutTTS);
        logoutTTS.setOnClickListener(v -> {
            String message = "Logout";
            speakText(message);
        });

        LinearLayout logoutButton = findViewById(R.id.hLogout);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        LinearLayout appliedButton = findViewById(R.id.hApplied);
        appliedButton.setOnClickListener(v -> {
            Intent intent = new Intent(Main.this, AppliedActivity.class);
            startActivity(intent);
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

    private void getUserInfo() {
        if (userId != -1) {
            String query = "SELECT name, gender FROM user WHERE id = " + userId;
            Cursor cursor = gateway.getData(database, query);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex("name");
                    int genderIndex = cursor.getColumnIndex("gender");

                    if (nameIndex != -1 && genderIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String gender = cursor.getString(genderIndex);
                        uName.setText("Hello " + name);

                        if ("male".equalsIgnoreCase(gender)) {
                            uPic.setImageResource(R.drawable.male);
                        } else if ("female".equalsIgnoreCase(gender)) {
                            uPic.setImageResource(R.drawable.female);
                        }
                    } else {
                        uName.setText("Required columns not found");
                    }
                    cursor.close();
                } else {
                    uName.setText("User not found");
                }
            } else {
                uName.setText("Database query failed");
            }
        } else {
            uName.setText("Invalid User ID");
        }
    }

    private void speakText(String text){
        initializeTextToSpeech();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}