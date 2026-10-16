package org.shivang.banking.service;


import lombok.RequiredArgsConstructor;
import org.shivang.banking.Exception.AccountNotFoundException;
import org.shivang.banking.Exception.InsufficientBalanceException;
import org.shivang.banking.Exception.InvalidTransferException;
import org.shivang.banking.model.Account;
import org.shivang.banking.model.AccountStatus;
import org.shivang.banking.repository.AccountRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepo accountRepo;

    public String generateAccountNumber() {
        String accountNumber = "ACC:" + UUID.randomUUID()
                .toString()
                .replace("-","")
                .substring(0,12)
                .toUpperCase();

        return accountNumber;
    }

    public Account createAccount(Account account) {
        Account createdAccount = new  Account();
        createdAccount.setBalance(BigDecimal.ZERO);
        createdAccount.setStatus(AccountStatus.ACTIVE);
        createdAccount.setAccountNumber(generateAccountNumber());
        createdAccount.setAccountType(account.getAccountType());
        createdAccount.setCreatedAt(LocalDateTime.now());
        accountRepo.save(createdAccount);
        return createdAccount;
    }

    public Account getAccountById(Long id){
       return accountRepo.findById(id)
               .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Transactional
    public Account deposit(Long id, BigDecimal amount) {
        Account account = accountRepo.findByIdForUpdate(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(amount ==  null || amount.compareTo(BigDecimal.ZERO) <=0){
            throw new InvalidTransferException("Amount must be greater than 0");

        }
        account.setBalance(account.getBalance().add(amount));
        return accountRepo.save(account);
    }

    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {
        Account account = accountRepo.findByIdForUpdate(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <=0){
            throw new InvalidTransferException("Amount must be greater than 0");
        }
        if(amount.compareTo(account.getBalance()) >0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepo.save(account);
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public void deleteAccount(Long id) {
       Account account  = accountRepo.findById(id)
               .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        accountRepo.delete(account);
    }
}
