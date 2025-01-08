package com.usp.mba.adapters.controller;

import com.usp.mba.adapters.persistence.entities.UserEntity;
import com.usp.mba.adapters.persistence.repositories.SpringDataUserRepository;
import com.usp.mba.adapters.persistence.mapper.UserMapper;
import com.usp.mba.adapters.rest.dto.UserRequestDTO;
import com.usp.mba.domain.enums.Technology;
import com.usp.mba.domain.model.Experience;
import com.usp.mba.domain.model.User;
import com.usp.mba.domain.ports.input.RegisterUserInputPort;
import com.usp.mba.domain.ports.input.UpdateUserInputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RegisterUserInputPort registerUserInputPort;
    private final UpdateUserInputPort updateUserInputPort;
    private final SpringDataUserRepository userRepository;


    public UserController(RegisterUserInputPort registerUserInputPort, SpringDataUserRepository userRepository, UpdateUserInputPort updateUserInputPort) {
        this.registerUserInputPort = registerUserInputPort;
        this.userRepository = userRepository;
        this.updateUserInputPort = updateUserInputPort;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable)
                .map(UserMapper::toDomain);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserEntity>> searchUsersByTechnologyAndExperience(
            @RequestParam Technology technology,
            @RequestParam int minYearsOfExperience,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository
                .findByExperiencePrimaryTechnologyAndExperienceYearsOfExperienceGreaterThanEqual(
                        technology, minYearsOfExperience, pageable
                );

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getRole() == null || userRequestDTO.getRole().isEmpty()) {
            throw new IllegalArgumentException("Role is mandatory and cannot be null or empty.");
        }

        // Criar a entidade Experience
        Experience experience = new Experience(
                userRequestDTO.getRole(),
                userRequestDTO.getYearsOfExperience(),
                userRequestDTO.getPrimaryTechnology(),
                userRequestDTO.getSecondaryTechnology()
        );

        // Criar a entidade User
        User user = new User(
                null,
                userRequestDTO.getName(),
                userRequestDTO.getEmail(),
                userRequestDTO.getPhone(),
                userRequestDTO.getLinkedInProfile(),
                experience
        );

        User createdUser = registerUserInputPort.registerUser(user);

        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {
        // Criar a entidade Experience a partir do DTO
        Experience experience = new Experience(
                userRequestDTO.getRole(),
                userRequestDTO.getYearsOfExperience(),
                userRequestDTO.getPrimaryTechnology(),
                userRequestDTO.getSecondaryTechnology()
        );

        // Criar o objeto User a partir do DTO
        User user = new User(
                id, // Usar o ID do caminho
                userRequestDTO.getName(),
                userRequestDTO.getEmail(),
                userRequestDTO.getPhone(),
                userRequestDTO.getLinkedInProfile(),
                experience

        );

        // Atualizar o usuário
        User updatedUser = updateUserInputPort.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }

}
