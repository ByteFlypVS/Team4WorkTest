package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextPhone, editTextPassword;
    private Spinner spinnerGender;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Initialize all views
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.password_input);
        spinnerGender = findViewById(R.id.spinnerGender);
        registerButton = findViewById(R.id.register_button);

        // Setup spinner for gender selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Set click listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement registration logic
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString().trim();

        if(name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Gateway dbGateway = new Gateway(this);
            boolean success = dbGateway.insertUser(name, phone, gender, password);
            if(success) {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
