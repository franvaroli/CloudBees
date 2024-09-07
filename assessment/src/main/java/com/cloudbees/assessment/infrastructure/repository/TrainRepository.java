package com.cloudbees.assessment.infrastructure.repository;

import com.cloudbees.assessment.domain.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {
}
