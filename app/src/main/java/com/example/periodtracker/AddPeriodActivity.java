package com.example.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddPeriodActivity extends AppCompatActivity {

    private TextView tvSelectedDate;
    private Button btnPickDate, btnSaveDate;
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_period);

        // Bind views
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnSaveDate = findViewById(R.id.btnSaveDate);

        // Pick date button
        btnPickDate.setOnClickListener(v -> showDatePicker());

        // Save date button
        btnSaveDate.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {

                PeriodDatabaseHelper db = new PeriodDatabaseHelper(this);

                // Insert selected date into database
                boolean success = db.insertPeriod(selectedDate);

                if (success) {
                    Toast.makeText(this, "Period date saved", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after saving
                } else {
                    Toast.makeText(this, "Error saving date", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show DatePickerDialog to select date
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    // Format: day/month/year
                    selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    tvSelectedDate.setText("Selected Date: " + selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
}
