package com.github.konstantinevashalomidze.kvashalomidze.repository;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Contact;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findFirstByOrderByIdDesc();
}
