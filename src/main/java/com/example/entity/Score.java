package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long studentId;
    
    @Column(nullable = false, length = 50)
    private String subject;
    
    @Column(nullable = false)
    private Integer score;
    
    @Column(length = 20)
    private String examType;
    
    private LocalDate examDate;
    
    private LocalDate createTime;
    
    private LocalDate updateTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDate.now();
        updateTime = LocalDate.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDate.now();
    }
}
