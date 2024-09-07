package com.cloudbees.assessment.application.service.userService;

import com.cloudbees.assessment.domain.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    List<User> getUsers(Long id, String name, String lastName, String email);
}
