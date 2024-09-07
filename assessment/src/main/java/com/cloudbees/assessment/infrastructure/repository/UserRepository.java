package com.cloudbees.assessment.infrastructure.repository;

import com.cloudbees.assessment.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIdOrNameOrLastNameOrEmail(Long id, String name, String lastName, String email);
}
