package com.javabackend.bankingapp.Entity;

import lombok.Data;

@Data
public class SendMoney {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    private String transactionPin;
}
