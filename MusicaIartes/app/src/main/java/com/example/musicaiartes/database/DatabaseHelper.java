package com.example.musicaiartes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MUSIC = "music";
    public static final String TABLE_PLAYLIST = "playlist";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PLAYLIST + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_MUSIC + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, artist TEXT, album TEXT, duration INTEGER, url TEXT, image_url TEXT, playlist_id INTEGER, " +
                "FOREIGN KEY(playlist_id) REFERENCES " + TABLE_PLAYLIST + "(id))");

        db.execSQL("INSERT INTO " + TABLE_PLAYLIST + " (name) VALUES ('Animada'), ('Triste'), ('Nostálgica'), ('Romântica')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade logic if needed
    }
}