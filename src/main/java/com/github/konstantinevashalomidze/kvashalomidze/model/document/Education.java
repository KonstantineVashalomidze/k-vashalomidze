package com.github.konstantinevashalomidze.kvashalomidze.model.document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title, instituteName, instituteUrl, logoFilePath;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Instant startDate, endDate;


}
