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

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Criação da tabela playlist
            db.execSQL("CREATE TABLE " + TABLE_PLAYLIST + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image_url TEXT)");

            // Criação da tabela music
            db.execSQL("CREATE TABLE " + TABLE_MUSIC + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "artist TEXT, " +
                    "album TEXT, " +
                    "duration TEXT, " +
                    "url TEXT, " +
                    "image_url TEXT, " +
                    "playlist_id INTEGER, " +
                    "FOREIGN KEY (playlist_id) REFERENCES playlist(id))"
            );

            // Inserção de playlists iniciais
            insertPlaylistWithImage(db, "Animada", "https://static.vecteezy.com/system/resources/previews/054/044/110/non_2x/a-joyful-beaming-emoji-face-with-smiling-eyes-and-a-wide-grin-exuding-happiness-and-positivity-with-bright-yellow-colors-and-simple-design-png.png");
            insertPlaylistWithImage(db, "Triste", "https://static.vecteezy.com/system/resources/previews/054/044/158/non_2x/a-frowning-emoji-face-with-a-deep-frown-and-downcast-eyes-conveying-sadness-and-disappointment-in-soft-yellow-tones-png.png");
            insertPlaylistWithImage(db, "Nostálgica", "https://cdn-icons-png.flaticon.com/256/3744/3744645.png");
            insertPlaylistWithImage(db, "Romântica", "https://www.pngplay.com/wp-content/uploads/6/Love-Emoji-PNG-Clipart-Background.png");

            Log.d("DatabaseHelper", "Banco de dados criado com sucesso.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Erro ao criar banco de dados", e);
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
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_PLAYLIST + " ADD COLUMN image_url TEXT");
        }
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MUSIC + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "artist TEXT, " +
                    "album TEXT, " +
                    "duration TEXT, " +
                    "url TEXT, " +
                    "image_url TEXT, " +
                    "playlist_id INTEGER, " +
                    "FOREIGN KEY (playlist_id) REFERENCES playlist(id))"
            );
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
