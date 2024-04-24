package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private Gateway dbGateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Log.d(TAG, "onCreate: Activity is being created.");

        dbGateway = new Gateway(this);  // Initialize the database gateway

        editTextUsername = findViewById(R.id.username_input);  // Assuming you have an EditText with id 'username_input'
        editTextPassword = findViewById(R.id.password_input);  // Assuming you have an EditText with id 'password_input'
        loginButton = findViewById(R.id.login_button);  // Assuming you have a Button with id 'login_button'

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity is starting.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity has resumed and is interacting with the user.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity is pausing and may be partially or fully obscured.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity is stopping and is no longer visible to the user.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity is about to be destroyed.");
    }

    private void authenticateUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbGateway.checkUserCredentials(username, password)) {
            Log.d(TAG, "Login successful");
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();  // Close the login activity
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
}
