package com.payflow.service;

import com.payflow.dto.AccountResponse;
import com.payflow.dto.CreateAccountRequest;
import com.payflow.dto.UpdateAccountRequest;
import com.payflow.entity.Account;
import com.payflow.entity.User;
import com.payflow.exception.BadRequestException;
import com.payflow.exception.ResourceNotFoundException;
import com.payflow.repository.AccountRepository;
import com.payflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // Get current logged-in user
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // Generate unique account number
    private String generateAccountNumber() {
        String accountNumber;
        Random random = new Random();
        do {
            accountNumber = String.format("ACC%010d", random.nextInt(1000000000));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    // Create new account
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        User user = getCurrentUser();

        // Generate unique account number
        String accountNumber = generateAccountNumber();

        // Create account
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    // Get all accounts for current user (with pagination)
    public Page<AccountResponse> getMyAccounts(int page, int size, String sortBy) {
        User user = getCurrentUser();

        // Create pageable with sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        // Fetch accounts
        Page<Account> accounts = accountRepository.findByUser(user, pageable);

        // Map to response DTOs
        return accounts.map(this::mapToResponse);
    }

    // Get account details by account number
    public AccountResponse getAccountByNumber(String accountNumber) {
        User user = getCurrentUser();

        Account account = accountRepository.findByAccountNumberAndUser(accountNumber, user)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        return mapToResponse(account);
    }

    // Get account balance
    public BigDecimal getBalance(String accountNumber) {
        User user = getCurrentUser();

        Account account = accountRepository.findByAccountNumberAndUser(accountNumber, user)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        return account.getBalance();
    }

    // Update account
    @Transactional
    public AccountResponse updateAccount(String accountNumber, UpdateAccountRequest request) {
        User user = getCurrentUser();

        Account account = accountRepository.findByAccountNumberAndUser(accountNumber, user)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        // Update fields
        if (request.getAccountType() != null) {
            account.setAccountType(request.getAccountType());
        }

        Account updatedAccount = accountRepository.save(account);

        return mapToResponse(updatedAccount);
    }

    // Delete account (soft delete - only if balance is zero)
    @Transactional
    public void deleteAccount(String accountNumber) {
        User user = getCurrentUser();

        Account account = accountRepository.findByAccountNumberAndUser(accountNumber, user)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found or access denied"));

        // Check if balance is zero
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BadRequestException("Cannot delete account with non-zero balance");
        }

        accountRepository.delete(account);
    }

    // Helper method to map entity to DTO
    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getCreatedAt());
    }
}