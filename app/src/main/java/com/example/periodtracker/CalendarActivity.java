package com.example.periodtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);

        // Load period data
        SharedPreferences prefs = getSharedPreferences("PeriodPrefs", MODE_PRIVATE);
        String lastDateStr = prefs.getString("last_period_date", "");
        String prevDateStr = prefs.getString("previous_period_date", "");

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());

        try {
            Calendar cal = Calendar.getInstance();

            // Highlight last period
            if (!lastDateStr.isEmpty()) {
                Date lastDate = sdf.parse(lastDateStr);
                cal.setTime(lastDate);
                calendarView.setDate(cal.getTimeInMillis(), true, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selected = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(this, "Selected date: " + selected, Toast.LENGTH_SHORT).show();
        });
    }
}
