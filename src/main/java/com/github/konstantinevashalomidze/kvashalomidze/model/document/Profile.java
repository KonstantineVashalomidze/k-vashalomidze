package com.github.konstantinevashalomidze.kvashalomidze.model.document;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, profession, imageFilePath;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    private Long charismaCount;

}
