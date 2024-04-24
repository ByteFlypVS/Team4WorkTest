package com.example.t4w;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView handImage = findViewById(R.id.hand_image);
        ObjectAnimator animator = ObjectAnimator.ofFloat(handImage, "rotation", 0f, 20f, -20f, 0f);
        animator.setDuration(600);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();

        TextView welcomeText = findViewById(R.id.welcome_text);
        String username = getIntent().getStringExtra("USERNAME"); // Ensure this key matches
        welcomeText.setText("Welcome, " + (username == null ? "User" : username) + "!");

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    greetUser();
                }
            }
        });

        Button beginButton = findViewById(R.id.start_button);
        beginButton.setOnClickListener(view -> {
            Log.d("WelcomeActivity", "Starting Main activity");
            Intent intent = new Intent(WelcomeActivity.this, Main.class);
            int userId = getIntent().getIntExtra("USER_ID", -1); // Retrieve the user ID passed from LoginActivity
            intent.putExtra("USER_ID", userId);  // Forward this user ID to Main
            startActivity(intent);
            finish();
        });
    }

    private void greetUser() {
        String username = getIntent().getStringExtra("USERNAME"); // Fetch again for speech
        tts.speak("Welcome, " + (username == null ? "User" : username), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}