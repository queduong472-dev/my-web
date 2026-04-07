package com.example.my_web;

import jakarta.persistence.*;

@Entity
public class Exercise {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String content;
    private boolean completed = false; 

    // 1. Constructor mặc định (Bắt buộc phải có cho JPA)
    public Exercise() {}

    // 2. Constructor đầy đủ để dùng trong Controller cho tiện
    public Exercise(Long id, String content, boolean completed) {
        this.id = id;
        this.content = content;
        this.completed = completed;
    }

    // 3. Constructor nhanh để thêm mới
    public Exercise(String content) { 
        this.content = content; 
        this.completed = false;
    }

    // --- GETTER & SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}