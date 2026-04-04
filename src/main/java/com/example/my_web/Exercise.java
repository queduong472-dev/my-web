package com.example.my_web;

import jakarta.persistence.*;

@Entity
public class Exercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean completed = false; // Mặc định là chưa làm (chưa tích)

    public Exercise() {}
    public Exercise(String content) { this.content = content; }

    // Getter & Setter
    public Long getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}