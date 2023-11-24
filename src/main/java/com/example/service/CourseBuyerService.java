package com.example.service;


import com.example.dto.CommonResponse;
import com.example.repository.CourseBuyerRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseBuyerService {

    private final CourseBuyerRepository courseBuyerRepository;

    public ResponseEntity<CommonResponse> createBuyer() {
        return null;
    }

    public ResponseEntity<CommonResponse> deleteBuyer() {
        return null;
    }

    public ResponseEntity<CommonResponse> getBuyingHistory() {
        return null;
    }

}
