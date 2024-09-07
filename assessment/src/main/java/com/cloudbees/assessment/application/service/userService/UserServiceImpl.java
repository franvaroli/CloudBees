package com.cloudbees.assessment.application.service.userService;

import com.cloudbees.assessment.domain.entity.User;
import com.cloudbees.assessment.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsers(Long id, String name, String lastName, String email) {
        return userRepository.findAllByIdOrNameOrLastNameOrEmail(id, name, lastName, email);
    }
}
