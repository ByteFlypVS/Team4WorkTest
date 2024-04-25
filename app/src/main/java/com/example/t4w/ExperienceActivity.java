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
    private LinearLayout inputFieldsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        jobsContainer = findViewById(R.id.job_entries_container);
        inputFieldsContainer = findViewById(R.id.input_fields_container);

        Button addExperienceButton = findViewById(R.id.add_experience_button);
        EditText jobTitleInput = findViewById(R.id.job_title_input);
        EditText companyNameInput = findViewById(R.id.company_name_input);

        addExperienceButton.setOnClickListener(v -> {
            if (inputFieldsContainer.getVisibility() == View.GONE) {
                inputFieldsContainer.setVisibility(View.VISIBLE);
            } else {
                addJobEntry(jobTitleInput.getText().toString(), companyNameInput.getText().toString());
                jobTitleInput.setText("");
                companyNameInput.setText("");
                inputFieldsContainer.setVisibility(View.GONE);
            }
        });
    }

    private void addJobEntry(String jobTitle, String companyName) {

        View jobEntryView = LayoutInflater.from(this).inflate(R.layout.job_entry, jobsContainer, false);

        ImageView jobImageView = jobEntryView.findViewById(R.id.jPic);
        TextView jobTitleView = jobEntryView.findViewById(R.id.jName);
        TextView companyNameView = jobEntryView.findViewById(R.id.jEmployer);


        jobImageView.setImageResource(R.drawable.workexperience);
        jobTitleView.setText(jobTitle);
        companyNameView.setText(companyName);

        jobsContainer.addView(jobEntryView);
    }
}
