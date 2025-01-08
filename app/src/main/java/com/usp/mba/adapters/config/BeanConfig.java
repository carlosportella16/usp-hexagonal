package com.usp.mba.adapters.config;

import com.usp.mba.adapters.persistence.repositories.JpaUserRepository;
import com.usp.mba.domain.ports.input.RegisterUserInputPort;
import com.usp.mba.domain.ports.input.UpdateUserInputPort;
import com.usp.mba.domain.service.InsertUserService;
import com.usp.mba.domain.service.UpdateUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public RegisterUserInputPort registerUserService(JpaUserRepository userRepository) {
        return new InsertUserService(userRepository);
    }

    @Bean
    public UpdateUserInputPort updateUserService(JpaUserRepository userRepository) {
        return new UpdateUserService(userRepository);
    }
}
