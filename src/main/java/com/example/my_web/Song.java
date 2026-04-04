package com.example.my_web;

import jakarta.persistence.*;

@Entity
public class Song {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;      // Tên bài hát
    private String spotifyId;  // Chỉ lưu cái mã ID của bài hát thôi (ví dụ: 1)

    public Song() {}
    public Song(String title, String spotifyId) {
        this.title = title;
        this.spotifyId = spotifyId;
    }
    // Getter & Setter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSpotifyId() { return spotifyId; }
    public void setSpotifyId(String spotifyId) { this.spotifyId = spotifyId; }
}