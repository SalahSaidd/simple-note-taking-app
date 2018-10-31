package com.said.simplenotetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class DbHelper extends SQLiteOpenHelper {
    public static final String TAG = "DbHelper";
    public static final String DB_NAME = "app_notes.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createNoteTable = "CREATE TABLE " + TABLE_NOTES + "(" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_BODY + " TEXT, " +
                COLUMN_CREATED_AT + " TEXT, " +
                COLUMN_UPDATED_AT + " TEXT " +
                ")";

        sqLiteDatabase.execSQL(createNoteTable);
        Log.d(TAG, createNoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }



    public long insertNote(SQLiteDatabase sqLiteDatabase, String title, String body) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_BODY, body);
        cv.put(COLUMN_CREATED_AT, year + "-" + month + "-" + day);
        cv.put(COLUMN_UPDATED_AT, year + "-" + month + "-" + day);

        return sqLiteDatabase.insert(TABLE_NOTES, null, cv);

    }

    public boolean updateNote(SQLiteDatabase sqLiteDatabase, long id, String title, String body) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_BODY, body);
        cv.put(COLUMN_UPDATED_AT, year + "-" + month + "-" + day);

        int numberOfRowsAffected = sqLiteDatabase.update(TABLE_NOTES, cv, COLUMN_ID + "=" + id, null);
        return numberOfRowsAffected > 0;
    }

        public boolean deleteNote(SQLiteDatabase sqLiteDatabase, long id) {
        int numberOfRowsDeleted = sqLiteDatabase.delete(TABLE_NOTES, COLUMN_ID + "=" + id, null);
        return numberOfRowsDeleted > 0;
    }

    public ArrayList<Notes> getAllNotes (SQLiteDatabase sqLiteDatabase){
           ArrayList<Notes> notes = new ArrayList<>();

           Cursor Cursor = sqLiteDatabase.query(TABLE_NOTES, null, null, null,
                   null, null, null);

            while(Cursor.moveToNext()){
                int id = Cursor.getInt(Cursor.getColumnIndex(COLUMN_ID));
                String title = Cursor.getString(Cursor.getColumnIndex(COLUMN_TITLE));
                String body = Cursor.getString(Cursor.getColumnIndex(COLUMN_BODY));
                String createdAt = Cursor.getString(Cursor.getColumnIndex(COLUMN_CREATED_AT));
                String updatedAt = Cursor.getString(Cursor.getColumnIndex(COLUMN_UPDATED_AT));

                Notes note = new Notes(id, title, body, createdAt, updatedAt);
                notes.add(note);
            }

                return notes;
    }
}