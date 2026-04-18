package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, unique = true, length = 20)
    private String studentNo;
    
    private Integer gender;
    
    private LocalDate birthday;
    
    @Column(length = 100)
    private String major;
    
    @Column(length = 20)
    private String clazz;
    
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
