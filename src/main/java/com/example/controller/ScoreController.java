package com.example.controller;

import com.example.entity.Score;
import com.example.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;
    
    @PostMapping
    public ResponseEntity<Score> createScore(@RequestBody Score score) {
        return new ResponseEntity<>(scoreService.createScore(score), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Score> getScoreById(@PathVariable Long id) {
        return scoreService.getScoreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        return ResponseEntity.ok(scoreService.getAllScores());
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Score>> getScoresByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(scoreService.getScoresByStudentId(studentId));
    }
    
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Score>> getScoresBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(scoreService.getScoresBySubject(subject));
    }
    
    @GetMapping("/student/{studentId}/subject/{subject}")
    public ResponseEntity<List<Score>> getScoresByStudentIdAndSubject(
            @PathVariable Long studentId,
            @PathVariable String subject) {
        return ResponseEntity.ok(scoreService.getScoresByStudentIdAndSubject(studentId, subject));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable Long id, @RequestBody Score scoreDetails) {
        return ResponseEntity.ok(scoreService.updateScore(id, scoreDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deleteScoresByStudentId(@PathVariable Long studentId) {
        scoreService.deleteScoresByStudentId(studentId);
        return ResponseEntity.noContent().build();
    }
}
