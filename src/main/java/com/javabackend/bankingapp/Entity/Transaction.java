package com.javabackend.bankingapp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String username;
    private String email;

    private String oppAccountNumber;
    private String oppUsername;
    private String oppEmail;

    private Double amount;
    private String type;
    private LocalDateTime timestamp = LocalDateTime.now();

}
