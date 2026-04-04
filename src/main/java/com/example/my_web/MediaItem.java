package com.example.my_web;

import jakarta.persistence.*;

@Entity
public class MediaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String imageUrl;
    private String status; // "finished", "in progress"
    private int rating;    // 1-5 sao
    private String type;   // "book", "movie", "series"

    public MediaItem() {}
    public MediaItem(Long id, String title, String imageUrl, String status, int rating, String type) {
        this.id = id; this.title = title; this.imageUrl = imageUrl; 
        this.status = status; this.rating = rating; this.type = type;
    }

    // Getter & Setter (Má nhớ chuột phải Generate hoặc viết tay nhe)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}