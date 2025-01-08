package com.usp.mba.adapters.persistence.repositories;

import com.usp.mba.adapters.persistence.entities.UserEntity;
import com.usp.mba.domain.enums.Technology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    // find by email
    Optional<UserEntity> findByEmail(String email);

    // find by primary technology and years of experience
    Page<UserEntity> findByExperiencePrimaryTechnologyAndExperienceYearsOfExperienceGreaterThanEqual(
            Technology primaryTechnology,
            int yearsOfExperience,
            Pageable pageable
    );

    // find all
    Page<UserEntity> findAll(Pageable pageable);
}
