package com.example.musicaiartes.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.musicaiartes.models.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDao {
    private final SQLiteDatabase db;
    private final Context context;

    public PlaylistDao(SQLiteDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    public List<Playlist> getAll() {
        List<Playlist> playlists = new ArrayList<>();
        // Atualizando a consulta para buscar o campo image_name
        Cursor cursor = db.rawQuery("SELECT id, name, image_name FROM playlist", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageName = cursor.getString(2); // Agora é um nome de imagem, não um BLOB

            // Verifique se o nome da imagem não é nulo
            if (imageName == null) {
                Log.e("PlaylistDao", "Nome da imagem nulo para playlist: " + name);
            }

            playlists.add(new Playlist(id, name, imageName)); // Passando o nome da imagem para o Playlist
        }
        cursor.close();

        Log.d("PlaylistDao", "Total de playlists recuperadas: " + playlists.size());
        return playlists;
    }
}
