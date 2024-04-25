package com.example.t4w;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AppliedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applied);

        // Find the back button using its ID and set an OnClickListener
        ImageButton backButton = findViewById(R.id.applied_jobs_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call finish to close this activity and return to the previous one
                finish();
            }
        });
    }
}
