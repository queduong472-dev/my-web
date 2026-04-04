package com.example.my_web;

import jakarta.persistence.*;

@Entity
public class AcademicTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String status; // "TODO", "DOING", "DONE"

    // Viết Constructor trống
    public AcademicTask() {}

    // Viết Constructor đầy đủ
    public AcademicTask(Long id, String content, String status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    // Má tạo Getter và Setter cho từng cái (Hoặc chuột phải chọn Source Action -> Generate Getters and Setters)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}