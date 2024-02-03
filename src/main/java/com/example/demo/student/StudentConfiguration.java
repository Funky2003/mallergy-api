package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student funky = new Student(
                    "1funky",
                    "setsofia.nusetor@google.com",
                    LocalDate.of(2000, Month.JUNE, 26)
            );

            Student thompson = new Student(
                    "thompson",
                    "michael.thompson@google.com",
                    LocalDate.of(1999, Month.JANUARY, 27)
            );

            studentRepository.saveAll(List.of(funky, thompson));
        };
    }
}
