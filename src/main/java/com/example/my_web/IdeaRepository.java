package com.example.my_web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    // Đây là Interface - thể hiện tính Trừu tượng (Abstraction) của OOP.
    // Bạn không cần viết code, Spring Boot sẽ tự hiểu cách Lưu/Xóa/Sửa.
}