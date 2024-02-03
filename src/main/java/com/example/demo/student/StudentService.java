package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> newStudent = studentRepository
                .findStudentByEmail(student.getEmail());
        if(newStudent.isPresent()) {
            throw new IllegalStateException ("Email exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("Student with ID: " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String studentName, String studentEmail) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("The student id " + studentId + " does not exist")
        );

        if (studentName != null && !studentName.isEmpty() && !Objects.equals(student.getName(), studentName)) {
            student.setName(studentName);
        }
        if (studentEmail != null && !studentEmail.isEmpty() && !Objects.equals(student.getEmail(), studentEmail)) {
            Optional<Student> optionalStudentEmail = studentRepository.findStudentByEmail(studentEmail);
            if (optionalStudentEmail.isPresent()) {
                throw new IllegalStateException("Email already exists");
            }
            student.setEmail(studentEmail);
        }
    }
}
