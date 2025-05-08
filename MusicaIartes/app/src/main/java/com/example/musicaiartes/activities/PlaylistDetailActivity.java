package com.example.musicaiartes.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
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

        adapter = new MusicaAdapter(musicas, musica -> showMusicaDetailDialog(musica));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Swipe para deletar
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) { return false; }

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

    private void showMusicaDetailDialog(Musica musica) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_musica_detail, null);

        ((TextView) view.findViewById(R.id.textMusicaNome)).setText(musica.getName());
        ((TextView) view.findViewById(R.id.textMusicaArtista)).setText(musica.getArtist());
        ((TextView) view.findViewById(R.id.textMusicaAlbum)).setText(musica.getAlbum());
        ((TextView) view.findViewById(R.id.textMusicaDuracao)).setText(musica.getDuration());

        TextView textLink = view.findViewById(R.id.textMusicaLink);
        textLink.setOnClickListener(v -> {
            String spotifyUrl = musica.getUrl();
            if (spotifyUrl != null && !spotifyUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(spotifyUrl));
                startActivity(intent);
            }
        });

        builder.setView(view);
        builder.setPositiveButton("Voltar", null);
        builder.create().show();
    }
}
