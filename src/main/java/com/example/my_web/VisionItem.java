package com.example.my_web;

import jakarta.persistence.*;

@Entity
public class VisionItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String imageUrl;

    // 1. Constructor không đối số (Bắt buộc phải có nhe má)
    public VisionItem() {
    }

    // 2. Constructor có đối số để mình dễ tạo mới
    public VisionItem(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    // 3. Getter và Setter - "Đôi tay" để lấy và cất dữ liệu
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}