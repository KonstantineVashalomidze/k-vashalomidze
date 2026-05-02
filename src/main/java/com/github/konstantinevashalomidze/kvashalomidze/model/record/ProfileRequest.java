package com.github.konstantinevashalomidze.kvashalomidze.model.record;

import java.util.List;

public record ProfileRequest(
        String hero,
        String about,
        List<ProjectRequest> projects,
        ContactRequest contact
) {
    public record ProjectRequest(
            String name,
            String description,
            String learnings,
            String liveUrl,
            String repoUrl,
            String techStack
    ) {}

    public record ContactRequest(
            String email,
            String githubUrl,
            String linkedinUrl
    ) {}
}