package com.example.musicaiartes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.musicaiartes.models.Musica;

public class MusicaDao {

    private SQLiteDatabase db;

    public MusicaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertMusic(Musica musica) {
        ContentValues values = new ContentValues();
        values.put("name", musica.getName());
        values.put("artist", musica.getArtist());
        values.put("album", musica.getAlbum());
        values.put("duration", musica.getDuration());
        values.put("url", musica.getUrl()); // Spotify URL
        values.put("image_url", musica.getImageUrl());
        values.put("playlist_id", musica.getPlaylistId());

        db.insert("music", null, values);
    }
}
