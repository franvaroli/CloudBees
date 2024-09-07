package com.cloudbees.assessment.infrastructure.controller;

import com.cloudbees.assessment.application.service.userService.UserService;
import com.cloudbees.assessment.domain.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Tag(name = "UserApi", description = "User API")
@RequiredArgsConstructor(onConstructor = @__({ @Autowired}))
@RequestMapping(value = "/user", produces = { "application/json" })
public class UserAPI {

    private final UserService userService;

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Call for getAllUsers");
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUser(@Parameter(description = "Id") Long id,
                                              @Parameter(description = "Name") String name,
                                              @Parameter(description = "Last name") String lastName,
                                              @Parameter(description = "Email") String email) {
        log.info("Call for getUser with params: {}", id, name, lastName, email);
        var users = userService.getUsers(id, name, lastName, email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
