package com.github.konstantinevashalomidze.kvashalomidze.model.document;

import jakarta.persistence.*;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone, email, linkedin, github, facebook;
    private String leetcode, hackerrank, codeforces, typeRacer;
    @Column(columnDefinition = "TEXT")
    private String typeRacerBadgeHtml;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLeetcode() {
        return leetcode;
    }

    public void setLeetcode(String leetcode) {
        this.leetcode = leetcode;
    }

    public String getHackerrank() {
        return hackerrank;
    }

    public void setHackerrank(String hackerrank) {
        this.hackerrank = hackerrank;
    }

    public String getCodeforces() {
        return codeforces;
    }

    public void setCodeforces(String codeforces) {
        this.codeforces = codeforces;
    }

    public String getTypeRacer() {
        return typeRacer;
    }

    public void setTypeRacer(String typeRacer) {
        this.typeRacer = typeRacer;
    }

    public String getTypeRacerBadgeHtml() {
        return typeRacerBadgeHtml;
    }

    public void setTypeRacerBadgeHtml(String typeRacerBadgeHtml) {
        this.typeRacerBadgeHtml = typeRacerBadgeHtml;
    }
}
