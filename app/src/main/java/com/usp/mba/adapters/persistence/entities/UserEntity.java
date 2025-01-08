package com.usp.mba.adapters.persistence.entities;

import com.usp.mba.domain.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String linkedInProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "experience_id", referencedColumnName = "id")
    private ExperienceEntity experience;

    public UserEntity(Long id, String name, String email, String phone, String linkedInProfile, ExperienceEntity experience) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.linkedInProfile = linkedInProfile;
        this.experience = experience;
    }

    public User toDomain() {
        return new User(
                id,
                name,
                email,
                phone,
                linkedInProfile,
                experience != null ? experience.toDomain() : null

        );
    }
}
