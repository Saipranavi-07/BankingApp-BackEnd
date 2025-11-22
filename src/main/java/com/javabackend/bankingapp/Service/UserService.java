package com.javabackend.bankingapp.Service;

import com.javabackend.bankingapp.Entity.User;
import com.javabackend.bankingapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        user.setAccountNumber(generateUniqueAccountNumber());
        user.setBalance(10000.0);
        user.setStatus("ACTIVE");

        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null && user.getPassword().equals(password)) {
            user.setStatus("ACTIVE");
            userRepository.save(user);
            return user;
        }
        return null;
    }

    private String generateUniqueAccountNumber() {

        return "ACC" + UUID.randomUUID().toString().substring(0, 8);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByAccountNumber(String accountNumber) {
        return userRepository.findById(accountNumber).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
