package com.example.periodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView tvLastPeriod, tvNextPeriod, tvCycleStatus;
    private Button btnAddPeriod, btnCalendar, btnArticles;

    private static final String PREFS_NAME = "PeriodPrefs";
    private static final String KEY_LAST = "last_period_date";
    private static final String KEY_PREV = "previous_period_date";
    private static final int AVERAGE_CYCLE_DAYS = 28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
        tvLastPeriod = findViewById(R.id.tvLastPeriod);
        tvNextPeriod = findViewById(R.id.tvNextPeriod);
        tvCycleStatus = findViewById(R.id.tvCycleStatus);
        btnAddPeriod = findViewById(R.id.btnAddPeriod);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnArticles = findViewById(R.id.btnArticles);

        // Load period data
        loadPeriodData();

        // Button listeners
        btnAddPeriod.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddPeriodActivity.class)));

        btnCalendar.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CalendarActivity.class)));

        btnArticles.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ArticlesActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPeriodData();
    }

    // Load period data and update UI
    private void loadPeriodData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String lastDate = prefs.getString(KEY_LAST, null);
        String prevDate = prefs.getString(KEY_PREV, null);

        if (lastDate != null) {
            tvLastPeriod.setText("Last Period: " + lastDate);
            tvNextPeriod.setText("Next Period: " + calculateNextPeriod(lastDate));
        } else {
            tvLastPeriod.setText("Last Period: Not set");
            tvNextPeriod.setText("Next Period: Not calculated");
        }

        if (lastDate != null && prevDate != null) {
            tvCycleStatus.setText("Cycle Status: " + checkCycleRegularity(prevDate, lastDate));
        } else {
            tvCycleStatus.setText("Cycle Status: Not evaluated");
        }
    }

    // Predict next period based on average cycle
    private String calculateNextPeriod(String lastDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
            Date date = sdf.parse(lastDate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, AVERAGE_CYCLE_DAYS);

            return sdf.format(cal.getTime());
        } catch (Exception e) {
            Log.e("MainActivity", "Error calculating next period", e);
            return "Error";
        }
    }

    // Check if cycle is regular or irregular
    private String checkCycleRegularity(String prevDate, String lastDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
            Date prev = sdf.parse(prevDate);
            Date last = sdf.parse(lastDate);

            long diffMillis = last.getTime() - prev.getTime();
            long cycleLength = TimeUnit.MILLISECONDS.toDays(diffMillis);

            if (Math.abs(cycleLength - AVERAGE_CYCLE_DAYS) > 5) {
                return "Irregular";
            } else {
                return "Regular";
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error evaluating cycle regularity", e);
            return "Not evaluated";
        }
    }
}
