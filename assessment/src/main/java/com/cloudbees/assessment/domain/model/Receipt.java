package com.cloudbees.assessment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {

    private Long idTicket;
    private String from;
    private String to;
    private String subject;
    private String price;
    private String seat;
}
