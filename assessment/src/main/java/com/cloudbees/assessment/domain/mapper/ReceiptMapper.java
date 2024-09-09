package com.cloudbees.assessment.domain.mapper;

import com.cloudbees.assessment.domain.entity.Ticket;
import com.cloudbees.assessment.domain.model.Receipt;

public class ReceiptMapper {

    public static Receipt fromTicket(Ticket ticket) {

        final String VALUE_SEPARATOR = " - ";

        if (ticket == null) {
            return null;
        }

        Receipt receipt = new Receipt();
        receipt.setFrom(ticket.getTrain().getFrom());
        receipt.setTo(ticket.getTrain().getTo());
        receipt.setSubject(ticket.getUser().getSubject());
        receipt.setPrice(ticket.getTrain().getPrice().toString());
        receipt.setSeat(ticket.getSection() + VALUE_SEPARATOR + seatTranslator(ticket.getSeatNum()));
        return receipt;
    }

    /**
     * Method that translates from a database seat number to a real seat number with row and letter, ie:1A
     * @param seatNum
     * @return seat
     */
    private static String seatTranslator(Integer seatNum) {
        return switch (seatNum % 4) {
            case 0 -> (seatNum / 4) + "D";
            case 1 -> (seatNum / 4 + 1) + "A";
            case 2 -> (seatNum / 4 + 1) + "B";
            case 3 -> (seatNum / 4 + 1) + "C";
            default -> throw new IllegalStateException("Unexpected value: " + seatNum );
        };
    }
}
