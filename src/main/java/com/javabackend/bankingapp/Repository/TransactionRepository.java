package com.javabackend.bankingapp.Repository;

import com.javabackend.bankingapp.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumberOrderByTimestampDesc(String accountNumber);
}
