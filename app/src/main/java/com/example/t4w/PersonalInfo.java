package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

public class PersonalInfo extends AppCompatActivity {

    TextView nameInfo, genderInfo, phoneInfo;
    ImageView profileImage;
    Gateway gateway;
    int userId;  // To hold the received user ID
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);

        nameInfo = findViewById(R.id.name_info);
        genderInfo = findViewById(R.id.gender_info);
        phoneInfo = findViewById(R.id.phone_info);
        profileImage = findViewById(R.id.profile_image);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        userId = getIntent().getIntExtra("USER_ID", -1); // Default to -1 if not found
        gateway = new Gateway(this);

        initializeTextToSpeech();
        setupListeners();
        getUserData();
    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    nameInfo.setText("Language not supported for TTS");
                }
            } else {
                nameInfo.setText("TTS initialization failed");
            }
        });
    }

    private void setupListeners() {
        nameInfo.setOnClickListener(v -> speak("Your name is " + nameInfo.getText().toString().replace("Name: ", "")));
        genderInfo.setOnClickListener(v -> speak("Your gender is " + genderInfo.getText().toString().replace("Gender: ", "")));
        phoneInfo.setOnClickListener(v -> speak("Your phone number is " + phoneInfo.getText().toString().replace("Phone Number: ", "")));
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void getUserData() {
        if (userId != -1) {
            PersonalInfo.UserInfo userInfo = gateway.fetchUserData(userId);
            if (userInfo != null) {
                nameInfo.setText("Name: " + userInfo.name);
                genderInfo.setText("Gender: " + userInfo.gender);
                phoneInfo.setText("Phone Number: " + userInfo.phone);
                updateProfileImage(userInfo.gender);
            } else {
                nameInfo.setText("User data not available.");
            }
        } else {
            nameInfo.setText("No user ID provided.");
        }
    }

    private void updateProfileImage(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            profileImage.setImageResource(R.drawable.male);
        } else if (gender.equalsIgnoreCase("female")) {
            profileImage.setImageResource(R.drawable.female);
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public static class UserInfo {
        String name, gender, phone;

        UserInfo(String name, String gender, String phone) {
            this.name = name;
            this.gender = gender;
            this.phone = phone;
        }
    }
}
