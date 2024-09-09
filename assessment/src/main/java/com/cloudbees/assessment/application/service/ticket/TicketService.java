package com.cloudbees.assessment.application.service.ticket;

import com.cloudbees.assessment.application.Section;
import com.cloudbees.assessment.domain.model.Receipt;

import java.util.List;

public interface TicketService {

    Receipt buyTicket(Long idTrain, Long idUser);
    List<Receipt> getReceipts(Long idTrain, Long idUser);
    List<Receipt> getReceiptsBySection(Long idTrain, Section section);
    void deleteTicket(Long idTicket);
    Receipt changeSeat(Long idTicket);
}
