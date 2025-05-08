package com.example.musicaiartes.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicaiartes.R;
import com.example.musicaiartes.adapters.MusicaAdapter;
import com.example.musicaiartes.database.DatabaseHelper;
import com.example.musicaiartes.models.Musica;

import java.util.List;

public class PlaylistDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MusicaAdapter adapter;
    private DatabaseHelper dbHelper;
    private int playlistId;
    private String playlistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        recyclerView = findViewById(R.id.recyclerMusicas);
        dbHelper = new DatabaseHelper(this);

        playlistId = getIntent().getIntExtra("playlistId", -1);
        playlistName = getIntent().getStringExtra("playlistName");

        setTitle("Playlist: " + playlistName);

        List<Musica> musicas = dbHelper.getMusicasByPlaylistId(playlistId);

        adapter = new MusicaAdapter(musicas, musica -> {
            String spotifyUrl = musica.getUrl(); // ou musica.getSpotifyUrl() dependendo do seu model
            if (spotifyUrl != null && !spotifyUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(spotifyUrl));
                intent.setPackage("com.spotify.music");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Spotify não instalado, abrir no navegador
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(spotifyUrl));
                    startActivity(webIntent);
                }
            } else {
                Toast.makeText(this, "Link do Spotify inválido.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Swipe para deletar
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Musica musica = musicas.get(viewHolder.getAdapterPosition());
                dbHelper.deleteMusica(musica.getId());
                musicas.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }
}
