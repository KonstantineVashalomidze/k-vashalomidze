package com.github.konstantinevashalomidze.kvashalomidze.repository;

import com.github.konstantinevashalomidze.kvashalomidze.model.document.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findTopByOrderByIdDesc();
}
