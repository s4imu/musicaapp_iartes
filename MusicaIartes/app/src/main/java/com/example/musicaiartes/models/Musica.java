package com.example.musicaiartes.models;

public class Musica {

    private int id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String url;
    private String imageUrl;
    private int playlistId;

    // Construtor com ID (para recuperar do banco)
    public Musica(int id, String name, String artist, String album, String duration, String url, String imageUrl, int playlistId) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.url = url;
        this.imageUrl = imageUrl;
        this.playlistId = playlistId;
    }

    // Construtor sem ID (para inserir no banco)
    public Musica(String name, String artist, String album, String duration, String url, String imageUrl, int playlistId) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.url = url;
        this.imageUrl = imageUrl;
        this.playlistId = playlistId;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getPlaylistId() { return playlistId; }
    public void setPlaylistId(int playlistId) { this.playlistId = playlistId; }
}
