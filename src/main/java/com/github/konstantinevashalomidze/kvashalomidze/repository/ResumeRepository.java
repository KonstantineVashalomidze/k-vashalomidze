package com.github.konstantinevashalomidze.kvashalomidze.repository;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {}
