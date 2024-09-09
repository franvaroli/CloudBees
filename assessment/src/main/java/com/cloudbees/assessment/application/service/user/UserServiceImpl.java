package com.cloudbees.assessment.application.service.user;

import com.cloudbees.assessment.domain.entity.User;
import com.cloudbees.assessment.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if(userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException("User with id " + user.getId() + " doesn't exist");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("User with id " + id + " doesn't exist");
        }
    }
}
