package com.cloudbees.assessment.application.service.ticket;

import com.cloudbees.assessment.application.Section;
import com.cloudbees.assessment.domain.entity.Ticket;
import com.cloudbees.assessment.domain.mapper.ReceiptMapper;
import com.cloudbees.assessment.domain.model.Receipt;
import com.cloudbees.assessment.infrastructure.repository.TicketRepository;
import com.cloudbees.assessment.infrastructure.repository.TrainRepository;
import com.cloudbees.assessment.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TicketServiceImpl implements TicketService {

    private static final String SEPARATOR = " ";

    private final TicketRepository ticketRepository;
    private final TrainRepository trainRepository;
    private final UserRepository userRepository;

    @Override
    public Receipt buyTicket(Long idTrain, Long idUser) {
        var user = userRepository.findById(idUser);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User with id " + idUser + " doesn't exist");
        }
        var seatTicket = seatGenerator(idTrain);
        var train = seatTicket.getTrain();
        var ticket = new Ticket(null, user.get(), train, seatTicket.getSection(), seatTicket.getSeatNum());
        ticketRepository.save(ticket);
        return ReceiptMapper.fromTicket(ticket);
    }

    @Override
    public List<Receipt> getReceipts(Long idTrain, Long idUser) {
        var user = userRepository.findById(idUser);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User with id " + idUser + " doesn't exist");
        }
        var train = trainRepository.findById(idTrain);
        if (train.isEmpty()) {
            throw new NoSuchElementException("Train with id " + idTrain + " doesn't exist");
        }
        var tickets = ticketRepository.findAllByTrainAndUser(train.get(), user.get());
        return tickets.stream().map(ReceiptMapper::fromTicket).toList();
    }

    @Override
    public List<Receipt> getReceiptsBySection(Long idTrain, Section section) {
        var train = trainRepository.findById(idTrain);
        if (train.isEmpty()) {
            throw new NoSuchElementException("Train with id " + idTrain + " doesn't exist");
        }
        var tickets = ticketRepository.findAllByTrainAndSection(train.get(), section);
        return tickets.stream().map(ReceiptMapper::fromTicket).toList();
    }

    @Override
    public void deleteTicket(Long idTicket) {
        var ticket = ticketRepository.findById(idTicket);
        if (ticket.isEmpty()) {
            throw new NoSuchElementException("Ticket with id " + idTicket + " doesn't exist");
        }
        ticketRepository.delete(ticket.get());
    }

    @Override
    public Receipt changeSeat(Long idTicket) {
        var ticket = ticketRepository.findById(idTicket);
        if (ticket.isEmpty()) {
            throw new NoSuchElementException("Ticket with id " + idTicket + " doesn't exist");
        }
        var newTicket = seatGenerator(ticket.get().getTrain().getId());
        newTicket.setId( ticket.get().getId() );
        newTicket.setUser(ticket.get().getUser());
        ticketRepository.save(newTicket);
        return ReceiptMapper.fromTicket(newTicket);
    }

    /**
     * Generates a random seat section and number that is not taken
     * @param idTrain
     * @return Ticket
     */
    private Ticket seatGenerator(Long idTrain) {
        var train = trainRepository.findById(idTrain);
        if (train.isEmpty()) {
            throw new NoSuchElementException("Train with id " + idTrain + " doesn't exist");
        }
        if (ticketRepository.countTicketsByTrain(train.get()) == 80) {
            throw new NoSuchElementException("Train is full");
        }
        //Section randomizer
        var random = new Random();
        var section = Section.values()[random.nextInt(Section.values().length)];
        var trainTickets = ticketRepository.findAllByTrain(train.get());
        Section finalSection = section;
        if (trainTickets.stream().filter(i -> i.getSection().equals(finalSection)).count() == 40) {
            section = section == Section.A ? Section.B : Section.A;
        }
        //Seat number randomizer
        var occupiedSeats =
                trainTickets.stream().filter(i -> i.getSection().equals(finalSection)).map(Ticket::getSeatNum).toList();
        int seatNum = 40;
        for (int i = 1; i < 40; i++) {
            if (!occupiedSeats.contains(i)) {
                seatNum = i;
                break;
            }
        }
        return new Ticket(null, null, train.get(), section, seatNum);
    }

}
