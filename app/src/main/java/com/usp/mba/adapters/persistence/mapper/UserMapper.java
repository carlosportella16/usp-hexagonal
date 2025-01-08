package com.usp.mba.adapters.persistence.mapper;

import com.usp.mba.adapters.persistence.entities.ExperienceEntity;
import com.usp.mba.adapters.persistence.entities.UserEntity;
import com.usp.mba.domain.model.Experience;
import com.usp.mba.domain.model.User;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        if (user == null) return null;

        ExperienceEntity experienceEntity = toEntity(user.getExperience());
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getLinkedInProfile(),
                experienceEntity
        );
    }

    public static User toDomain(UserEntity userEntity) {
        if (userEntity == null) return null;

        Experience experience = toDomain(userEntity.getExperience());
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                userEntity.getLinkedInProfile(),
                experience
        );
    }

    private static ExperienceEntity toEntity(Experience experience) {
        if (experience == null) return null;

        return new ExperienceEntity(
                experience.getRole() != null ? experience.getRole() : "N/A", // Valor padrão
                experience.getYearsOfExperience(),
                experience.getPrimaryTechnology(),
                experience.getSecondaryTechnology()
        );
    }

    private static Experience toDomain(ExperienceEntity experienceEntity) {
        if (experienceEntity == null) return null;

        return new Experience(
                experienceEntity.getRole() != null ? experienceEntity.getRole() : "N/A", // Valor padrão
                experienceEntity.getYearsOfExperience(),
                experienceEntity.getPrimaryTechnology(),
                experienceEntity.getSecondaryTechnology()
        );
    }
}
