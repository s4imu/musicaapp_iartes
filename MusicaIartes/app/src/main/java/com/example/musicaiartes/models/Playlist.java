package com.example.musicaiartes.models;

public class Playlist {

    private int id;
    private String name;
    private String imageUrl;  // URL da imagem

    public Playlist(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return name; // Exibe apenas o nome da playlist
    }
}
