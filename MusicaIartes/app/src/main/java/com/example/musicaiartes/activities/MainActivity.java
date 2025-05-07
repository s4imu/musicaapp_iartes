package com.example.musicaiartes.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.example.musicaiartes.R;
import com.example.musicaiartes.adapters.TrackAdapter;
import com.example.musicaiartes.models.TrackItem;
import com.example.musicaiartes.network.SpotifyAuthHelper;
import com.example.musicaiartes.network.SpotifySearchHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private EditText edtSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private TrackAdapter adapter;
    private String CLIENT_ID;
    private String CLIENT_SECRET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new TrackAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String query = edtSearch.getText().toString();
            if (!query.isEmpty()) {
                buscarMusicas(query);
            }
        });
    }

    private void buscarMusicas(String termo) {
        new Thread(() -> {
            String token = SpotifyAuthHelper.getAccessToken();
            if (token != null) {
                List<TrackItem> resultados = SpotifySearchHelper.searchTracks(token, termo);

                runOnUiThread(() -> adapter.updateList(resultados));
            } else {
                runOnUiThread(() ->
                        Toast.makeText(this, "Erro ao obter token", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
