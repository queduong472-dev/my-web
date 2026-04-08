package com.example.my_web;

import jakarta.persistence.*;

@Entity
@Table(name = "daily_entry")
public class DailyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String date;
    private String grateful;
    private int rating;
    private String emotion;

    // Constructor mặc định (bắt buộc phải có cho JPA)
    public DailyEntry() {}

    // Constructor có tham số để dùng trong Controller
    public DailyEntry(String date, String grateful, int rating, String emotion) {
        this.date = date;
        this.grateful = grateful;
        this.rating = rating;
        this.emotion = emotion;
    }

    // --- GETTER & SETTER (Viết tay cho má nè) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getGrateful() { return grateful; }
    public void setGrateful(String grateful) { this.grateful = grateful; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getEmotion() { return emotion; }
    public void setEmotion(String emotion) { this.emotion = emotion; }
}