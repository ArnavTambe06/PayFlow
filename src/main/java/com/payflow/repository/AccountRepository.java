package com.payflow.repository;

import com.payflow.entity.Account;
import com.payflow.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find all accounts for a specific user (with pagination)
    Page<Account> findByUser(User user, Pageable pageable);

    // Find account by account number
    Optional<Account> findByAccountNumber(String accountNumber);

    // Find account by account number and user (ensures user owns this account)
    Optional<Account> findByAccountNumberAndUser(String accountNumber, User user);

    // Check if account number already exists
    Boolean existsByAccountNumber(String accountNumber);

    // Count accounts for a user
    Long countByUser(User user);
}