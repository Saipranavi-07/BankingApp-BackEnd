package com.javabackend.bankingapp.Repository;

import com.javabackend.bankingapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}
