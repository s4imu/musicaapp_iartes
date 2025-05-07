package com.example.musicaiartes.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicaiartes.R;
import com.example.musicaiartes.database.DatabaseHelper;
import com.example.musicaiartes.database.MusicaDao;
import com.example.musicaiartes.database.PlaylistDao;
import com.example.musicaiartes.models.Musica;
import com.example.musicaiartes.models.Playlist;
import com.example.musicaiartes.models.TrackItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackDetailActivity extends AppCompatActivity {

    private TextView txtTrack, txtArtist, txtAlbum, txtDuration, txtSpotifyLink;
    private ImageView imgAlbum;
    private Spinner spinner;
    private Button btnSalvar, btnVoltar;

    private List<Playlist> playlists;
    private Playlist selectedPlaylist;
    private TrackItem track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        txtTrack = findViewById(R.id.txtTrack);
        txtArtist = findViewById(R.id.txtArtist);
        txtAlbum = findViewById(R.id.txtAlbum);
        txtDuration = findViewById(R.id.txtDuration);
        txtSpotifyLink = findViewById(R.id.txtSpotifyLink);
        imgAlbum = findViewById(R.id.imgAlbum);
        spinner = findViewById(R.id.spinnerPlaylists);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        track = (TrackItem) getIntent().getSerializableExtra("track");

        if (track != null) {
            txtTrack.setText(track.trackName);
            txtArtist.setText(track.artistName);
            txtAlbum.setText(track.albumName);
            txtDuration.setText(track.duration);
            txtSpotifyLink.setText(track.spotifyUrl);
            Glide.with(this).load(track.imageUrl).into(imgAlbum);
        }

        DatabaseHelper helper = new DatabaseHelper(this);
        PlaylistDao playlistDao = new PlaylistDao(helper.getReadableDatabase());
        playlists = playlistDao.getAll();

        ArrayAdapter<Playlist> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, playlists);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPlaylist = playlists.get(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSalvar.setOnClickListener(v -> {
            if (track != null && selectedPlaylist != null) {
                Musica music = new Musica(
                        track.trackName,
                        track.artistName,
                        track.albumName,
                        track.duration,
                        track.spotifyUrl,
                        track.imageUrl,
                        selectedPlaylist.getId()
                );
                MusicaDao musicDao = new MusicaDao(helper.getWritableDatabase());
                musicDao.insertMusic(music);
                Toast.makeText(this, "Música salva com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnVoltar.setOnClickListener(v -> finish());
    }
}
