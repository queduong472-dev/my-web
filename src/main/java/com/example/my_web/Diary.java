package com.example.my_web;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob 
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String createdAt;

    public Diary() {}
    public Diary(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy"));
    }

    // --- GETTERS (Để lấy dữ liệu ra) ---
    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getCreatedAt() { return createdAt; }

    // --- SETTERS (QUAN TRỌNG: Để cập nhật dữ liệu khi bạn bấm SỬA) ---
    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}