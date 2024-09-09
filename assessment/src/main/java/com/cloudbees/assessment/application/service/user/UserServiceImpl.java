package com.cloudbees.assessment.application.service.user;

import com.cloudbees.assessment.domain.dto.UserDto;
import com.cloudbees.assessment.domain.entity.User;
import com.cloudbees.assessment.domain.mapper.UserMapper;
import com.cloudbees.assessment.infrastructure.repository.TicketRepository;
import com.cloudbees.assessment.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        var user = userRepository.findById(id);
        if(user.isPresent()) {
            return userRepository.save(UserMapper.fromDto(id, userDto, user.get()));
        } else {
            throw new NoSuchElementException("User with id " + id + " doesn't exist");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if(userRepository.existsById(id)) {
            if(!ticketRepository.existsTicketByUserId(id)) {
                userRepository.deleteById(id);
            } else {
                throw new NoSuchElementException("There is at least one ticket for user with id " + id);
            }
        } else {
            throw new NoSuchElementException("User with id " + id + " doesn't exist");
        }
    }
}
