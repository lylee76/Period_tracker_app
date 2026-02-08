package com.example.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSymptomActivity extends AppCompatActivity {

    CheckBox cbCramps, cbHeadache, cbMood;
    Button btnSave;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom);

        cbCramps = findViewById(R.id.cbCramps);
        cbHeadache = findViewById(R.id.cbHeadache);
        cbMood = findViewById(R.id.cbMood);
        btnSave = findViewById(R.id.btnSaveSymptom);

        btnSave.setOnClickListener(v -> {
            PeriodDatabaseHelper db = new PeriodDatabaseHelper(this);

            String today = new SimpleDateFormat("d/M/yyyy", Locale.getDefault())
                    .format(new Date());

            int cramps = cbCramps.isChecked() ? 1 : 0;
            int headache = cbHeadache.isChecked() ? 1 : 0;
            int mood = cbMood.isChecked() ? 1 : 0;

            db.insertSymptom(today, cramps, headache, mood);
            finish();
        });
    }
}
