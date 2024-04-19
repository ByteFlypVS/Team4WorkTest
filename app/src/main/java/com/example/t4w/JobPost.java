package com.example.t4w;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class JobPost extends AppCompatActivity{
    ImageView ePic;
    TextView job, employer, role, salary;
    String pName, pEmployer, pDesc, pePic;
    long pSalary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppost);

        Intent intent = getIntent();

        pePic = intent.getStringExtra("pePic");
        pName = intent.getStringExtra("pName");
        pEmployer = intent.getStringExtra("pEmployer");
        pDesc = intent.getStringExtra("pDesc");
        pSalary = intent.getLongExtra("pSalary", 0);

        ePic = findViewById(R.id.pePic);
        job = findViewById(R.id.pName);
        employer = findViewById(R.id.pEmployer);
        role = findViewById(R.id.pDesc);
        salary = findViewById(R.id.pSalary);

        job.setText(pName);
        employer.setText(pEmployer);
        role.setText(pDesc);
        salary.setText("€" + pSalary);

        try{
            InputStream input = this.getAssets().open(pePic);
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            ePic.setImageBitmap(bitmap);
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
