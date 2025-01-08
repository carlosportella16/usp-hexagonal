package com.usp.mba.domain.service;

import com.usp.mba.domain.enums.Technology;
import com.usp.mba.domain.model.Experience;
import com.usp.mba.domain.model.User;
import com.usp.mba.domain.ports.input.RegisterUserInputPort;
import com.usp.mba.domain.ports.output.UserRepository;

import java.util.Optional;

public class InsertUserService implements RegisterUserInputPort {

    private final UserRepository userRepository;

    public InsertUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        // Verifica se o email já está cadastrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Valida a experiência
        validateExperience(user.getExperience());

        // Persiste o usuário
        return userRepository.save(user);
    }

    private void validateExperience(Experience experience) {
        if (experience == null) {
            throw new IllegalArgumentException("Experience cannot be null");
        }

        // Valida os anos de experiência
        if (experience.getYearsOfExperience() < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative");
        }

        // Valida as tecnologias
        validateTechnology(experience.getPrimaryTechnology(), "Primary technology");
        if (experience.getSecondaryTechnology() != null) {
            validateTechnology(experience.getSecondaryTechnology(), "Secondary technology");
        }
    }

    private void validateTechnology(Technology technology, String fieldName) {
        if (technology == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }

        // Valida se a tecnologia está dentro do conjunto permitido (opcional, já garantido pelo enum)
        if (!isValidTechnology(technology)) {
            throw new IllegalArgumentException(fieldName + " is not a valid technology: " + technology);
        }
    }

    private boolean isValidTechnology(Technology technology) {
        for (Technology validTech : Technology.values()) {
            if (validTech == technology) {
                return true;
            }
        }
        return false;
    }

}
