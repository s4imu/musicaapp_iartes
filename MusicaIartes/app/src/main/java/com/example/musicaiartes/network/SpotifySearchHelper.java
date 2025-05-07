package com.example.musicaiartes.network;

import com.example.musicaiartes.models.TrackItem;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class SpotifySearchHelper {

    public static List<TrackItem> searchTracks(String accessToken, String query) {
        List<TrackItem> trackList = new ArrayList<>();

        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String endpoint = "https://api.spotify.com/v1/search?q=" + encodedQuery + "&type=track";

            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                br.close();

                JSONObject json = new JSONObject(response.toString());
                JSONArray items = json.getJSONObject("tracks").getJSONArray("items");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject track = items.getJSONObject(i);
                    String trackName = track.getString("name");
                    String artistName = track.getJSONArray("artists").getJSONObject(0).getString("name");
                    String albumName = track.getJSONObject("album").getString("name");
                    String imageUrl = track.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
                    int durationMs = track.getInt("duration_ms");
                    String spotifyUrl = track.getJSONObject("external_urls").getString("spotify");

                    // Calculando os minutos e segundos
                    int minutes = (durationMs / 1000) / 60;
                    int seconds = (durationMs / 1000) % 60;

                    // Formatando a duração para "m:ss"
                    String durationFormatted = String.format("%d:%02d", minutes, seconds);

                    trackList.add(new TrackItem(trackName, artistName, albumName, imageUrl, durationFormatted, spotifyUrl));
                }
            } else {
                System.out.println("Erro na busca: HTTP " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackList;
    }
}
