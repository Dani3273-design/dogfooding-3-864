package com.example.repository;

import com.example.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByStudentId(Long studentId);
    
    List<Score> findBySubject(String subject);
    
    List<Score> findByStudentIdAndSubject(Long studentId, String subject);
    
    void deleteByStudentId(Long studentId);
}
