package com.example.musicaiartes.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.musicaiartes.models.Playlist;
import java.util.*;

public class PlaylistDao {
    private final SQLiteDatabase db;

    public PlaylistDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Playlist> getAll() {
        List<Playlist> playlists = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, name FROM playlist", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            playlists.add(new Playlist(id, name));
        }
        cursor.close();
        return playlists;
    }
}