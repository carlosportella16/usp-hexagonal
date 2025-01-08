package com.usp.mba.domain.service;

import com.usp.mba.domain.enums.Technology;
import com.usp.mba.domain.model.Experience;
import com.usp.mba.domain.model.User;
import com.usp.mba.domain.ports.output.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class InsertUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InsertUserService insertUserService;

    private User validUser;
    private Experience validExperience;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configura dados de teste
        validExperience = new Experience("Senior developer", 5,Technology.JAVA, Technology.PYTHON);
        validUser = new User(1L, "John Doe", "john.doe@example.com","41549216","www.linkedin.com", validExperience);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Configura o comportamento do mock
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(validUser)).thenReturn(validUser);

        // Executa o teste
        User result = insertUserService.registerUser(validUser);

        // Valida o resultado
        assertNotNull(result);
        assertEquals(validUser, result);

        verify(userRepository).findByEmail(validUser.getEmail());
        verify(userRepository).save(validUser);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Configura o mock para simular email existente
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.of(validUser));

        // Executa e verifica a exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                insertUserService.registerUser(validUser)
        );

        assertEquals("User with this email already exists", exception.getMessage());
        verify(userRepository).findByEmail(validUser.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenExperienceIsNull() {
        // Configura usuário com experiência nula
        User userWithNullExperience = new User(2L, "Jane Doe", "jane.doe@example.com", "12345678", "",null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                insertUserService.registerUser(userWithNullExperience)
        );

        assertEquals("Experience cannot be null", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenYearsOfExperienceIsNegative() {
        // Configura experiência com anos negativos
        Experience invalidExperience = new Experience("Developer", -1, Technology.JAVA, null);
        User userWithInvalidExperience = new User(3L, "Jane Doe", "jane.doe@example.com", "12333256", "www.linkedin.com", invalidExperience);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                insertUserService.registerUser(userWithInvalidExperience)
        );

        assertEquals("Years of experience cannot be negative", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPrimaryTechnologyIsNull() {
        Experience invalidExperience = new Experience("Developer",2, null, null);
        User userWithInvalidTech = new User(3L,"Jane Doe", "jane.doe@example.com", "12333256", "www.linkedin.com", invalidExperience);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                insertUserService.registerUser(userWithInvalidTech)
        );

        assertEquals("Primary technology cannot be null", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

}
