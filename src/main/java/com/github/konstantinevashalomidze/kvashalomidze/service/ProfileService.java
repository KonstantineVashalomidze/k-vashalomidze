package com.github.konstantinevashalomidze.kvashalomidze.service;


import com.github.konstantinevashalomidze.kvashalomidze.model.document.Contact;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Profile;
import com.github.konstantinevashalomidze.kvashalomidze.model.document.Project;
import com.github.konstantinevashalomidze.kvashalomidze.model.record.ProfileRequest;
import com.github.konstantinevashalomidze.kvashalomidze.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    @Transactional(readOnly = true)
    public Profile getLatest() {
        return profileRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new EntityNotFoundException("No profile found"));
    }

    @Transactional
    public Profile create(ProfileRequest request) {
        Contact contact = new Contact();
        contact.setEmail(request.contact().email());
        contact.setGithubUrl(request.contact().githubUrl());
        contact.setLinkedinUrl(request.contact().linkedinUrl());

        Profile profile = new Profile();
        profile.setHero(request.hero());
        profile.setAbout(request.about());
        profile.setContact(contact);

        List<Project> projects = request.projects().stream()
                .map(p -> {
                    Project project = new Project();
                    project.setName(p.name());
                    project.setDescription(p.description());
                    project.setLearnings(p.learnings());
                    project.setLiveUrl(p.liveUrl());
                    project.setRepoUrl(p.repoUrl());
                    project.setTechStack(p.techStack());
                    project.setProfile(profile);
                    return project;
                })
                .toList();

        profile.getProjects().addAll(projects);

        return profileRepository.save(profile);
    }
}