package com.usp.mba.domain.ports.input;

import com.usp.mba.domain.model.User;

public interface UpdateUserInputPort {
    User updateUser(Long id, User updatedUser);
}
