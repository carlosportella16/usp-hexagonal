package com.usp.mba.domain.model;


import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String linkedInProfile;
    private Experience experience;

    public User(Long id, String name, String email, String phone, String linkedInProfile, Experience experience) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.linkedInProfile = linkedInProfile;
        this.experience = experience;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Experience getExperience() { return experience; }
    public void setExperience(Experience experience) { this.experience = experience; }

    public String getLinkedInProfile() { return linkedInProfile; }
    public void setLinkedInProfile(String linkedInProfile) { this.linkedInProfile = linkedInProfile; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
