package com.example.service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentNo(student.getStudentNo())) {
            throw new RuntimeException("学号已存在");
        }
        return studentRepository.save(student);
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Optional<Student> getStudentByStudentNo(String studentNo) {
        return studentRepository.findByStudentNo(studentNo);
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public List<Student> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major);
    }
    
    public List<Student> getStudentsByClazz(String clazz) {
        return studentRepository.findByClazz(clazz);
    }
    
    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(studentDetails.getName());
                    student.setGender(studentDetails.getGender());
                    student.setBirthday(studentDetails.getBirthday());
                    student.setMajor(studentDetails.getMajor());
                    student.setClazz(studentDetails.getClazz());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("学生不存在"));
    }
    
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
