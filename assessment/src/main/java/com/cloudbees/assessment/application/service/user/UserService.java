package com.cloudbees.assessment.application.service.user;

import com.cloudbees.assessment.domain.dto.UserDto;
import com.cloudbees.assessment.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
