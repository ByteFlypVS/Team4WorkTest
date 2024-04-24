package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExperienceActivity extends AppCompatActivity {

    private LinearLayout jobsContainer;
    private LinearLayout inputFieldsContainer;  // Reference to the input fields container

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience);

        // Set up the back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Initialize the jobs container
        jobsContainer = findViewById(R.id.job_entries_container);
        inputFieldsContainer = findViewById(R.id.input_fields_container); // Initialize input fields container

        // Find the buttons and input fields
        Button addExperienceButton = findViewById(R.id.add_experience_button);
        EditText jobTitleInput = findViewById(R.id.job_title_input);
        EditText companyNameInput = findViewById(R.id.company_name_input);

        // Set up the listener for the "Add Experience" button
        addExperienceButton.setOnClickListener(v -> {
            if (inputFieldsContainer.getVisibility() == View.GONE) {
                inputFieldsContainer.setVisibility(View.VISIBLE); // Make the input fields visible
            } else {
                addJobEntry(jobTitleInput.getText().toString(), companyNameInput.getText().toString());
                jobTitleInput.setText(""); // Clear the input field
                companyNameInput.setText(""); // Clear the input field
                inputFieldsContainer.setVisibility(View.GONE); // Hide the input fields after adding the experience
            }
        });
    }

    private void addJobEntry(String jobTitle, String companyName) {
        // Inflate a new job entry view and set up its contents
        View jobEntryView = LayoutInflater.from(this).inflate(R.layout.job_entry, jobsContainer, false);

        ImageView jobImageView = jobEntryView.findViewById(R.id.jPic);
        TextView jobTitleView = jobEntryView.findViewById(R.id.jName);
        TextView companyNameView = jobEntryView.findViewById(R.id.jEmployer);

        // Assuming you have a drawable named "workexperience.png" in your drawable folder
        jobImageView.setImageResource(R.drawable.workexperience);
        jobTitleView.setText(jobTitle);
        companyNameView.setText(companyName);

        // Add the new job entry view to the container
        jobsContainer.addView(jobEntryView);
    }
}
