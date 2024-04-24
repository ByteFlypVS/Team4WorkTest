package com.example.t4w;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.d("WelcomeActivity", "Activity started");

        ImageView handImage = findViewById(R.id.hand_image);
        ObjectAnimator animator = ObjectAnimator.ofFloat(handImage, "rotation", 0f, 20f, -20f, 0f);
        animator.setDuration(600);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();

        Button beginButton = findViewById(R.id.start_button);
        beginButton.setOnClickListener(view -> {
            Log.d("WelcomeActivity", "Starting Main activity");
            Intent intent = new Intent(WelcomeActivity.this, Main.class);
            startActivity(intent);
            finish();
        });
    }
}