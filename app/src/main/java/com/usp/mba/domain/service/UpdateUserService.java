package com.usp.mba.domain.service;

import com.usp.mba.domain.model.Experience;
import com.usp.mba.domain.model.User;
import com.usp.mba.domain.ports.input.UpdateUserInputPort;
import com.usp.mba.domain.ports.output.UserRepository;

import java.util.Optional;

public class UpdateUserService implements UpdateUserInputPort {

    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }

        User existingUser = existingUserOpt.get();

        // Atualizar informações pessoais
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setLinkedInProfile(updatedUser.getLinkedInProfile());

        // Atualizar experiência
        Experience updatedExperience = updatedUser.getExperience();
        Experience existingExperience = existingUser.getExperience();
        if (updatedExperience != null) {
            existingExperience.setRole(updatedExperience.getRole());
            existingExperience.setYearsOfExperience(updatedExperience.getYearsOfExperience());
            existingExperience.setPrimaryTechnology(updatedExperience.getPrimaryTechnology());
            existingExperience.setSecondaryTechnology(updatedExperience.getSecondaryTechnology());
        }

        return userRepository.save(existingUser);
    }
}
