package com.usp.mba.adapters.persistence.entities;

import com.usp.mba.domain.enums.Technology;
import com.usp.mba.domain.model.Experience;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@Table(name = "experiences")
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private int yearsOfExperience;
    @Enumerated(EnumType.STRING)
    private Technology primaryTechnology;

    @Enumerated(EnumType.STRING)
    private Technology secondaryTechnology;
    public ExperienceEntity() {}

    public ExperienceEntity(String role, int yearsOfExperience, Technology primaryTechnology, Technology secondaryTechnology) {
        this.role = role;
        this.yearsOfExperience = yearsOfExperience;
        this.primaryTechnology = primaryTechnology;
        this.secondaryTechnology = secondaryTechnology;
    }

    public Experience toDomain() {
        return new Experience(role, yearsOfExperience, primaryTechnology, secondaryTechnology);
    }
}
