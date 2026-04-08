package com.example.my_web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEntryRepository extends JpaRepository<DailyEntry, Long> {
}