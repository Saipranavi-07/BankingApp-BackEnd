package com.javabackend.bankingapp.Controller;

import com.javabackend.bankingapp.Entity.User;
import com.javabackend.bankingapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/api")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://banking-app-frontend-rakesh.netlify.app"
})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        User user = userService.authenticateUser(email, password);
        if (user != null) {
            user.setPassword(null);
            user.setTransactionPin(null);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        users.forEach(u -> {
            u.setPassword(null);
            u.setTransactionPin(null);
        });
        return ResponseEntity.ok(users);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody Map<String, String> request) {
        String accountNumber = request.get("accountNumber");
        if (accountNumber == null) {
            return ResponseEntity.badRequest().body("Account number is required");
        }

        User user = userService.findByAccountNumber(accountNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user.setStatus("Inactive");
        userService.saveUser(user);

        return ResponseEntity.ok("Logout successful, status set to Inactive");
    }


    @GetMapping("/user/{accountNumber}")
    public ResponseEntity<?> getUserByAccountNumber(@PathVariable String accountNumber) {

        User user = userService.findByAccountNumber(accountNumber);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }


        user.setPassword(null);
        user.setTransactionPin(null);

        return ResponseEntity.ok(user);
    }


}
