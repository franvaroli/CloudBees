package com.cloudbees.assessment.infrastructure.repository;

import com.cloudbees.assessment.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
