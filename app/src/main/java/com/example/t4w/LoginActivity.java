package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final String TAG = "LoginActivity";
    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private Gateway dbGateway;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbGateway = new Gateway(this);
        tts = new TextToSpeech(this, this);

        editTextUsername = findViewById(R.id.username_input);
        editTextPassword = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);

        editTextUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) tts.speak("What is your name", TextToSpeech.QUEUE_FLUSH, null, null);
        });

        editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) tts.speak("Enter your password", TextToSpeech.QUEUE_FLUSH, null, null);
        });

        loginButton.setOnClickListener(v -> authenticateUser());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void authenticateUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbGateway.checkUserCredentials(username, password)) {
            int userID = dbGateway.getUserID(username);
            if (userID != -1) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                intent.putExtra("USER_ID", userID);  // Pass user ID to WelcomeActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show();
            }
        } else {
            tts.speak("Wrong username or password, please try again", TextToSpeech.QUEUE_FLUSH, null, null);
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
}
