package com.example.musicaiartes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicaiartes.R;
import com.example.musicaiartes.adapters.PlaylistAdapter;
import com.example.musicaiartes.database.DatabaseHelper;
import com.example.musicaiartes.database.PlaylistDao;
import com.example.musicaiartes.models.Playlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private DatabaseHelper dbHelper;
    private BottomNavigationView bottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ou activity_playlists

        recyclerView = findViewById(R.id.recyclerPlaylists);

        try {
            dbHelper = new DatabaseHelper(this);
            PlaylistDao playlistDao = new PlaylistDao(dbHelper.getReadableDatabase(), this);
            List<Playlist> playlists = playlistDao.getAll();

            adapter = new PlaylistAdapter(this, playlists, playlist -> {
                Intent intent = new Intent(MainActivity.this, PlaylistDetailActivity.class);
                intent.putExtra("playlistId", playlist.getId());
                intent.putExtra("playlistName", playlist.getName());
                startActivity(intent);
            });

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(adapter);

            bottomMenu = findViewById(R.id.bottomNavigation);

            bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_home) {
                        //TODO Verificar se já está na tela de home
                        return true;
                    }
                    else if (item.getItemId() == R.id.nav_search) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    else return false;
                }
            });

            Log.d("MainActivity", "Banco de dados carregado com sucesso e adapter configurado.");
        } catch (Exception e) {
            Log.e("MainActivity", "Erro ao inicializar o banco de dados ou a interface", e);
        }
    }

}
