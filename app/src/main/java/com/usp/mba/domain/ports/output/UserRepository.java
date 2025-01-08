package com.usp.mba.domain.ports.output;

import com.usp.mba.domain.enums.Technology;
import com.usp.mba.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);

    Page<User> findByPrimaryTechnologyAndYearsOfExperience(Technology primaryTechnology, int minYearsOfExperience, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);
}
