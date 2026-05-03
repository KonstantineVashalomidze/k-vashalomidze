package com.github.konstantinevashalomidze.kvashalomidze.repository;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    boolean existsByEmail(String email);
}
