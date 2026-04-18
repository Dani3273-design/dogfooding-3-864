package com.example.service;

import com.example.entity.Score;
import com.example.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;
    
    public Score createScore(Score score) {
        return scoreRepository.save(score);
    }
    
    public Optional<Score> getScoreById(Long id) {
        return scoreRepository.findById(id);
    }
    
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }
    
    public List<Score> getScoresByStudentId(Long studentId) {
        return scoreRepository.findByStudentId(studentId);
    }
    
    public List<Score> getScoresBySubject(String subject) {
        return scoreRepository.findBySubject(subject);
    }
    
    public List<Score> getScoresByStudentIdAndSubject(Long studentId, String subject) {
        return scoreRepository.findByStudentIdAndSubject(studentId, subject);
    }
    
    public Score updateScore(Long id, Score scoreDetails) {
        return scoreRepository.findById(id)
                .map(score -> {
                    score.setSubject(scoreDetails.getSubject());
                    score.setScore(scoreDetails.getScore());
                    score.setExamType(scoreDetails.getExamType());
                    score.setExamDate(scoreDetails.getExamDate());
                    return scoreRepository.save(score);
                })
                .orElseThrow(() -> new RuntimeException("成绩不存在"));
    }
    
    public void deleteScore(Long id) {
        scoreRepository.deleteById(id);
    }
    
    @Transactional
    public void deleteScoresByStudentId(Long studentId) {
        scoreRepository.deleteByStudentId(studentId);
    }
}
