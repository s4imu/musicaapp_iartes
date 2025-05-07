package com.example.musicaiartes.models;

import java.io.Serializable;

public class TrackItem implements Serializable {

    public String trackName;
    public String artistName;
    public String albumName;
    public String imageUrl;
    public String duration;
    public String spotifyUrl;

    public TrackItem(String trackName, String artistName, String albumName, String imageUrl, String duration, String spotifyUrl) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.spotifyUrl = spotifyUrl;
    }
}
