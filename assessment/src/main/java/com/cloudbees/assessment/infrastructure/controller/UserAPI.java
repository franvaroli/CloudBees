package com.cloudbees.assessment.infrastructure.controller;


import com.cloudbees.assessment.application.service.user.UserService;
import com.cloudbees.assessment.domain.dto.UserDto;
import com.cloudbees.assessment.domain.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Tag(name = "UserApi", description = "User API")
@RequestMapping(value = "/users")
@RestController
public class UserAPI {

    private final UserService userService;

    @GetMapping(value = "/")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Call for getAllUsers");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<User> createUser(@Parameter(description = "Name") @Size(min = 1, max = 20) String name,
                                           @Parameter(description = "Last name") @Size(min = 1, max = 20) String lastName,
                                           @Parameter(description = "Email") @Size(min = 1, max = 25) String email) {
        var user = new User(null, name, lastName, email);
        log.info("Call for createUser with params:{}", user);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<User> updateUser(@Parameter(description = "User Id") @Min(value = 1) @Max(value = 10) Long id,
                                           @Valid @RequestBody UserDto userDto) {
        log.info("Call for updateUser with id: " + id +" and params:{}", userDto);
        return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteUser(@Parameter(description = "Id") @Min(value = 1) @Max(value = 10) Long id) {
        log.info("Call for deleteUser with params:{}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
