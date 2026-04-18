package com.example.repository;

import com.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentNo(String studentNo);
    
    List<Student> findByMajor(String major);
    
    List<Student> findByClazz(String clazz);
    
    boolean existsByStudentNo(String studentNo);
}
