package com.example.my_web;
import jakarta.persistence.*;

@Entity
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content; 
    private String routineSession; // Dùng cái này cho an toàn với SQL
    private boolean completed;

    public Routine() {}
    public Routine(String content, String routineSession) {
        this.content = content;
        this.routineSession = routineSession;
        this.completed = false;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getRoutineSession() { return routineSession; }
    public void setRoutineSession(String routineSession) { this.routineSession = routineSession; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}