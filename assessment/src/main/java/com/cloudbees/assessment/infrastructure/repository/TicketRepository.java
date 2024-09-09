package com.cloudbees.assessment.infrastructure.repository;

import com.cloudbees.assessment.application.Section;
import com.cloudbees.assessment.domain.entity.Ticket;
import com.cloudbees.assessment.domain.entity.Train;
import com.cloudbees.assessment.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    int countTicketsByTrain(Train train);

    List<Ticket> findAllByTrain(Train train);

    List<Ticket> findAllByTrainAndUser(Train train, User user);

    List<Ticket> findAllByTrainAndSection(Train train, Section section);

    boolean existsTicketByUserId(Long id);

    boolean existsTicketByTrainId(Long id);
}
