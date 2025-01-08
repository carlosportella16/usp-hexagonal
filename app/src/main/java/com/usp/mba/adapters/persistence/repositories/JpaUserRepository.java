package com.usp.mba.adapters.persistence.repositories;

import com.usp.mba.adapters.persistence.mapper.UserMapper;
import com.usp.mba.adapters.persistence.entities.UserEntity;
import com.usp.mba.domain.enums.Technology;
import com.usp.mba.domain.model.User;
import com.usp.mba.domain.ports.output.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository springDataUserRepository;

    public JpaUserRepository(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        UserEntity savedEntity = springDataUserRepository.save(userEntity);
        return UserMapper.toDomain(savedEntity);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public Page<User> findByPrimaryTechnologyAndYearsOfExperience(Technology primaryTechnology, int minYearsOfExperience, Pageable pageable) {
        return springDataUserRepository
                .findByExperiencePrimaryTechnologyAndExperienceYearsOfExperienceGreaterThanEqual(primaryTechnology, minYearsOfExperience, pageable)
                .map(UserMapper::toDomain);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return springDataUserRepository.findAll(pageable)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id).map(UserMapper::toDomain);
    }
}
