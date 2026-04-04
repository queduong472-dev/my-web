package com.example.my_web;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AcademicTaskRepository extends JpaRepository<AcademicTask, Long> {
    List<AcademicTask> findByStatus(String status);
}