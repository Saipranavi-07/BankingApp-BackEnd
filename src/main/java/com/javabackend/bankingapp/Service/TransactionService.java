package com.javabackend.bankingapp.Service;


import com.javabackend.bankingapp.Entity.Transaction;
import com.javabackend.bankingapp.Entity.User;
import com.javabackend.bankingapp.Repository.TransactionRepository;
import com.javabackend.bankingapp.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TransactionRepository txRepo;

    @Transactional
    public String sendMoney(String senderAcc, String receiverAcc, Double amount, String transactionPin) {
        User sender = userRepo.findById(senderAcc).orElse(null);
        User receiver = userRepo.findById(receiverAcc).orElse(null);

        if(sender == null || receiver == null) return "Invalid account";

        if(!sender.getTransactionPin().equals(transactionPin))
            return "Invalid transaction pin";

        if(sender.getBalance() < amount)
            return "Insufficient balance";

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepo.save(sender);
        userRepo.save(receiver);

        Transaction senderTx = new Transaction();
        senderTx.setAccountNumber(sender.getAccountNumber());
        senderTx.setUsername(sender.getUsername());
        senderTx.setEmail(sender.getEmail());
        senderTx.setAmount(amount);
        senderTx.setType("debit");
        senderTx.setOppAccountNumber(receiver.getAccountNumber());
        senderTx.setOppUsername(receiver.getUsername());
        senderTx.setOppEmail(receiver.getEmail());
        txRepo.save(senderTx);

        Transaction receiverTx = new Transaction();
        receiverTx.setAccountNumber(receiver.getAccountNumber());
        receiverTx.setUsername(receiver.getUsername());
        receiverTx.setEmail(receiver.getEmail());
        receiverTx.setAmount(amount);
        receiverTx.setType("credit");
        receiverTx.setOppAccountNumber(sender.getAccountNumber());
        receiverTx.setOppUsername(sender.getUsername());
        receiverTx.setOppEmail(sender.getEmail());
        txRepo.save(receiverTx);

        return "Transaction Successful";
    }

    public List<Transaction> getTransactions(String accountNumber) {
        return txRepo.findByAccountNumberOrderByTimestampDesc(accountNumber);
    }
}
