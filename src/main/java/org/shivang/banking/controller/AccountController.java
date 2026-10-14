package org.shivang.banking.controller;

import lombok.RequiredArgsConstructor;
import org.shivang.banking.model.Account;
import org.shivang.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/id:{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
            Account account = accountService.getAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @GetMapping("/number:{accountNumber}")
    public ResponseEntity<?> getAccountByAccountNumber(@PathVariable String accountNumber) {
         Account account = accountService.getAccountByAccountNumber(accountNumber);
         return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<?> getBalance(@PathVariable Long id) {
            Account account = accountService.getAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(account.getBalance());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @RequestBody BigDecimal amount) {
            Account depositedaccount = accountService.deposit(id, amount);
            return ResponseEntity.status(HttpStatus.OK).body(depositedaccount);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestBody BigDecimal amount) {
            Account withdrawnaccount = accountService.withdraw(id, amount);
            return ResponseEntity.status(HttpStatus.OK).body(withdrawnaccount);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
