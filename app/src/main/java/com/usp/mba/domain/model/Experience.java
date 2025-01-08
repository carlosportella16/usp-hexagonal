package com.usp.mba.domain.model;

import com.usp.mba.domain.enums.Technology;

import java.util.Objects;

public class Experience {
    private String role;
    private int yearsOfExperience;
    private Technology primaryTechnology;
    private Technology secondaryTechnology;

    public Experience(String role, int yearsOfExperience, Technology primaryTechnology, Technology secondaryTechnology) {
        this.role = role;
        this.yearsOfExperience = yearsOfExperience;
        this.primaryTechnology = primaryTechnology;
        this.secondaryTechnology = secondaryTechnology;
    }

    // Getters e Setters
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public Technology getPrimaryTechnology() { return primaryTechnology; }
    public void setPrimaryTechnology(Technology primaryTechnology) { this.primaryTechnology = primaryTechnology; }
    public Technology getSecondaryTechnology() { return secondaryTechnology; }
    public void setSecondaryTechnology(Technology secondaryTechnology) { this.secondaryTechnology = secondaryTechnology; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
