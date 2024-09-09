package com.cloudbees.assessment.application.service;

import com.cloudbees.assessment.application.Section;
import com.cloudbees.assessment.application.service.ticket.TicketServiceImpl;
import com.cloudbees.assessment.domain.entity.Ticket;
import com.cloudbees.assessment.domain.entity.Train;
import com.cloudbees.assessment.domain.entity.User;
import com.cloudbees.assessment.infrastructure.repository.TicketRepository;
import com.cloudbees.assessment.infrastructure.repository.TrainRepository;
import com.cloudbees.assessment.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void shouldGetReceiptsOK() {
        Long ticketId = 1L;
        Long idTrain = 10L;
        Long idUser = 10L;
        User mockUser = User.builder().id(idUser).name("Jhon").lastName("Doe").email("jhondoe@gmail.com").build();
        Train mockTrain = Train.builder().id(idTrain).from("London").to("France").price(20.0).build();
        Ticket mockTicket =
                Ticket.builder().id(1L).user(mockUser).train(mockTrain).section(Section.A).seatNum(1).build();
        List<Ticket> mockTickets = new ArrayList<>();
        mockTickets.add(mockTicket);

        when(userRepository.findById(idUser)).thenReturn(Optional.of(mockUser));
        when(trainRepository.findById(idTrain)).thenReturn(Optional.of(mockTrain));
        when(ticketRepository.findAllByTrainAndUser(mockTrain, mockUser)).thenReturn(mockTickets);

        var receipt = ticketService.getReceipts(idTrain, idUser);

        assertEquals(ticketId, receipt.get(0).getIdTicket());
    }

    @Test
    void shouldGetReceiptsErrorUserNotFound() {
        Long idTrain = 10L;
        Long idUser = 10L;
        assertThrows(NoSuchElementException.class, () -> ticketService.getReceipts(idTrain, idUser));
    }

    @Test
    void shouldGetReceiptsErrorTrainNotFound() {
        Long idTrain = 10L;
        Long idUser = 10L;
        User mockUser = User.builder().id(idUser).name("Jhon").lastName("Doe").email("jhondoe@gmail.com").build();

        when(userRepository.findById(idUser)).thenReturn(Optional.of(mockUser));

        assertThrows(NoSuchElementException.class, () -> ticketService.getReceipts(idTrain, idUser));
    }
}
