package com.javabackend.bankingapp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String accountNumber;

    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String transactionPin;
    private Double balance;
    private String status;

}
