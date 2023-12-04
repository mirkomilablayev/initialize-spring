package com.example.service;

import com.example.repository.EnrollmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrolmentService {

    private final EnrollmentRepository enrollmentRepository;

    
}