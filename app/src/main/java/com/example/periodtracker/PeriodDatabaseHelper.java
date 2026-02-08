package com.example.periodtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PeriodDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "periods.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "periods";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "period_date";

    public PeriodDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT)";
        db.execSQL(createTable);

        String symptomTable = "CREATE TABLE symptoms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT," +
                "cramps INTEGER," +
                "headache INTEGER," +
                "mood INTEGER)";
        db.execSQL(symptomTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new period date
    public boolean insertPeriod(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, date);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    // Get all period dates
    public ArrayList<String> getAllPeriods() {
        ArrayList<String> periods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                periods.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return periods;
    }

    // Get the last period date
    public String getLastPeriod() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DATE + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE + " DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            String last = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            cursor.close();
            return last;
        } else {
            cursor.close();
            return null;
        }
    }

    // Get the previous period date
    public String getPreviousPeriod() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DATE + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE + " DESC LIMIT 1 OFFSET 1", null);
        if (cursor.moveToFirst()) {
            String prev = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            cursor.close();
            return prev;
        } else {
            cursor.close();
            return null;
        }
    }

    // Insert symptoms
    public boolean insertSymptom(String date, int cramps, int headache, int mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", date);
        cv.put("cramps", cramps);
        cv.put("headache", headache);
        cv.put("mood", mood);
        long result = db.insert("symptoms", null, cv);
        return result != -1;
    }

}
