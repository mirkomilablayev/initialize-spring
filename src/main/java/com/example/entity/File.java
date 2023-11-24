package com.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalName;
    @Column(nullable = false)
    private String generatedName;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private Long size;

}
