package com.example.musicaiartes.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;



public class SpotifyAuthHelper {

    public static String getAccessToken() {
        try {


            String clientId = "e07cd0f59268408695626e8e86edc331";
            String clientSecret = "fd4b637517f6402ea50555e228e5a0b9";

            if (clientId == null || clientSecret == null) {
                System.out.println("Variáveis de ambiente não encontradas.");
                return null;
            }

            String endpoint = "https://accounts.spotify.com/api/token";
            String params = "grant_type=client_credentials";

            String credentials = clientId + ":" + clientSecret;
            String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Basic " + encoded);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes("utf-8"));
            }

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                br.close();

                JSONObject json = new JSONObject(response.toString());
                return json.getString("access_token");
            } else {
                System.out.println("Erro ao obter token: HTTP " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}