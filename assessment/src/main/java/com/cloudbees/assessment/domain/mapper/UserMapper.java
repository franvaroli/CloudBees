package com.cloudbees.assessment.domain.mapper;


import com.cloudbees.assessment.domain.dto.UserDto;
import com.cloudbees.assessment.domain.entity.User;

public class UserMapper {

    public static User fromDto(Long id, UserDto userDto, User user) {
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setName(userDto.getName() != null ? userDto.getName() : user.getName());
        updateUser.setLastName(userDto.getLastName() != null ? userDto.getLastName() : user.getLastName());
        updateUser.setEmail(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail());
        return updateUser;
    }
}
