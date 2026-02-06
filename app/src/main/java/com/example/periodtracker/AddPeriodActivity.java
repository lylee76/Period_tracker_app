package com.example.periodtracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddPeriodActivity extends AppCompatActivity {

    TextView tvSelectedDate;
    Button btnPickDate, btnSaveDate;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_period);

        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnSaveDate = findViewById(R.id.btnSaveDate);

        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    (view, y, m, d) -> {
                        selectedDate = d + "/" + (m + 1) + "/" + y;
                        tvSelectedDate.setText("Selected Date: " + selectedDate);
                    },
                    year, month, day
            );
            dialog.show();
        });

        btnSaveDate.setOnClickListener(v -> {
            finish(); // we will save data in next step
        });
    }
}
