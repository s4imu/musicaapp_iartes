package com.example.musicaiartes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.musicaiartes.R;
import com.example.musicaiartes.models.Musica;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 2;  // Incrementa a versão para a atualização

    public static final String TABLE_MUSIC = "music";
    public static final String TABLE_PLAYLIST = "playlist";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            // Criação da tabela playlist com o campo image_url como TEXT
            db.execSQL("CREATE TABLE " + TABLE_PLAYLIST + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image_url TEXT)");

            // Inserção de playlists com as URLs das imagens
            insertPlaylistWithImage(db, "Animada", "https://drive.google.com/file/d/13NgSLHx4GFxxYVgkeOewhDAcM4XrJlyL/view?usp=sharing");
            insertPlaylistWithImage(db, "Triste", "https://static.vecteezy.com/system/resources/previews/054/044/158/non_2x/a-frowning-emoji-face-with-a-deep-frown-and-downcast-eyes-conveying-sadness-and-disappointment-in-soft-yellow-tones-png.png");
            insertPlaylistWithImage(db, "Nostálgica", "https://drive.google.com/file/d/1OcuUrVTt2Q2Udk7sj2xNCaSpVpePxFlb/view?usp=drive_link");
            insertPlaylistWithImage(db, "Romântica", "https://drive.google.com/file/d/1OcuUrVTt2Q2Udk7sj2xNCaSpVpePxFlb/view?usp=drive_link");

            Log.d("DatabaseHelper", "Banco de dados criado e playlists inseridas com sucesso.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Erro ao criar banco de dados ou inserir playlists", e);
        }
    }

    private void insertPlaylistWithImage(SQLiteDatabase db, String name, String imageUrl) {
        try {
            // Inserir a playlist com a URL da imagem
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("image_url", imageUrl);  // Agora armazenando o link da imagem
            long result = db.insert(TABLE_PLAYLIST, null, values);

            if (result == -1) {
                Log.e("DatabaseHelper", "Erro ao inserir playlist: " + name);
            } else {
                Log.d("DatabaseHelper", "Playlist inserida com sucesso: " + name);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Erro ao inserir playlist com imagem", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualização para versão 2, com campo image_url na playlist
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_PLAYLIST + " ADD COLUMN image_url TEXT");
        }
    }

    public List<Musica> getMusicasByPlaylistId(int playlistId) {
        List<Musica> musicas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM music WHERE playlist_id = ?", new String[]{String.valueOf(playlistId)});
        while (cursor.moveToNext()) {
            Musica musica = new Musica(
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("artist")),
                    cursor.getString(cursor.getColumnIndexOrThrow("album")),
                    cursor.getString(cursor.getColumnIndexOrThrow("duration")),
                    cursor.getString(cursor.getColumnIndexOrThrow("url")),
                    cursor.getString(cursor.getColumnIndexOrThrow("image_url")),
                    playlistId
            );
            musica.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            musicas.add(musica);
        }
        cursor.close();
        return musicas;
    }

    public void deleteMusica(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("music", "id = ?", new String[]{String.valueOf(id)});
    }
}
