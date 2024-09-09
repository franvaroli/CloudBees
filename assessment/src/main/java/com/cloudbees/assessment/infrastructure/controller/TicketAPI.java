package com.cloudbees.assessment.infrastructure.controller;

import com.cloudbees.assessment.application.Section;
import com.cloudbees.assessment.application.service.ticket.TicketService;
import com.cloudbees.assessment.domain.model.Receipt;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Tag(name = "TicketApi", description = "Ticket API")
@RequestMapping(value = "/tickets")
@RestController
public class TicketAPI {

    private final TicketService ticketService;

    @PostMapping(value = "/buy")
    public ResponseEntity<Receipt> buyTicket(@Parameter(description = "Train Id") @Min(value = 1) @Max(value = 10) Long idTrain,
                                             @Parameter(description = "User Id") @Min(value = 1) @Max(value = 10) Long idUser) {
        var receipt = ticketService.buyTicket(idTrain, idUser);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @GetMapping(value = "/receipt")
    public ResponseEntity<List<Receipt>> getReceipts(@Parameter(description = "Train Id") @Min(value = 1) @Max(value = 10) Long idTrain,
                                                     @Parameter(description = "User Id") @Min(value = 1) @Max(value = 10) Long idUser) {
        var receipts = ticketService.getReceipts(idTrain, idUser);
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @GetMapping(value = "/sectionReceipt")
    public ResponseEntity<List<Receipt>> getReceiptsBySection(@Parameter(description = "Train Id") @Min(value = 1) @Max(value = 10) Long idTrain,
                                                              @Parameter(description = "Section") Section section) {
        var receipts = ticketService.getReceiptsBySection(idTrain, section);
        return new ResponseEntity<>(receipts, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<HttpStatus> deleteTicket(@Parameter(description = "Ticket Id") @Min(value = 1) @Max(value = 10) Long idTicket) {
        ticketService.deleteTicket(idTicket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/changeSeat")
    public ResponseEntity<Receipt> changeSeat(@Parameter(description = "Ticket Id") @Min(value = 1) @Max(value = 10) Long idTicket) {
        return new ResponseEntity<>(ticketService.changeSeat(idTicket), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
