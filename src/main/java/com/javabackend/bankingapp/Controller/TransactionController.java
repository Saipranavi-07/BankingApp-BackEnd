package com.javabackend.bankingapp.Controller;


import com.javabackend.bankingapp.Entity.SendMoney;
import com.javabackend.bankingapp.Entity.Transaction;
import com.javabackend.bankingapp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;


    @PostMapping("/sendMoney")
    public ResponseEntity<?> sendMoney(@RequestBody SendMoney request) {
        String result = transactionService.sendMoney(
                request.getSenderAccountNumber(),
                request.getReceiverAccountNumber(),
                request.getAmount(),
                request.getTransactionPin()
        );

        if ("Transaction Successful".equals(result)) {
            return ResponseEntity.ok(Map.of("message", result));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", result));
        }
    }

    // Endpoint to fetch transactions for a given account number, ordered by timestamp descending
    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getTransactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }

}
