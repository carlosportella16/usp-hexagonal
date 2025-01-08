package com.usp.mba.domain.ports.input;

import com.usp.mba.domain.model.User;

public interface RegisterUserInputPort {
    User registerUser(User user);
}
