package com.example.my_web;
import jakarta.persistence.*;

@Entity
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Column(length = 1000) // Link ảnh thường dài nên để 1000 cho chắc
    private String imageUrl;

    public Artwork() {}
    public Artwork(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    // Getters & Setters (QUAN TRỌNG: Phải có đủ để Java không báo lỗi đỏ)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}