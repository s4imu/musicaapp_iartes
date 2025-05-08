package com.example.musicaiartes.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.musicaiartes.models.Musica;
import java.util.ArrayList;
import java.util.List;

public class MusicaDao {
    private final SQLiteDatabase db;

    public MusicaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertMusic(Musica musica) {
        ContentValues values = new ContentValues();
        values.put("name", musica.getName());
        values.put("artist", musica.getArtist());
        values.put("album", musica.getAlbum());
        values.put("duration", musica.getDuration());
        values.put("url", musica.getUrl());
        values.put("image_url", musica.getImageUrl());
        values.put("playlist_id", musica.getPlaylistId());
        db.insert("music", null, values);
    }

    public List<Musica> getByPlaylistId(int playlistId) {
        List<Musica> musicas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, name, artist, album, duration, url, image_url, playlist_id FROM music WHERE playlist_id = ?", new String[]{String.valueOf(playlistId)});
        while (cursor.moveToNext()) {
            Musica musica = new Musica(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
            musicas.add(musica);
        }
        cursor.close();
        return musicas;
    }

    public void deleteById(int id) {
        db.delete("music", "id = ?", new String[]{String.valueOf(id)});
    }

    public boolean exists(String url) {
        Cursor cursor = db.rawQuery("SELECT id FROM music WHERE url = ?", new String[]{url});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}
