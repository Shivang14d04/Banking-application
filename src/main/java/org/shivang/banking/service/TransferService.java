
package org.shivang.banking.service;

import lombok.RequiredArgsConstructor;
import org.shivang.banking.Exception.AccountNotFoundException;
import org.shivang.banking.Exception.InsufficientBalanceException;
import org.shivang.banking.Exception.InvalidTransferException;
import org.shivang.banking.model.Account;
import org.shivang.banking.model.Transfer;
import org.shivang.banking.model.TransferStatus;
import org.shivang.banking.model.dtos.TransferRequest;
import org.shivang.banking.repository.AccountRepo;
import org.shivang.banking.repository.TransferRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepo transferRepo;
    private final AccountService accountService;
    private final AccountRepo accountRepo;

    @Transactional
    public Transfer transferMoney(TransferRequest request) {

        String fromNumber = request.fromAccount();
        String toNumber = request.toAccount();
        BigDecimal amount = request.amount();

        if (fromNumber == null || toNumber == null) {
            throw new InvalidTransferException(
                    "Source and destination accounts are required");
        }

        if (fromNumber.equals(toNumber)) {
            throw new InvalidTransferException(
                    "Cannot transfer to the same account");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferException(
                    "Amount must be greater than zero");
        }

        String firstNumber;
        String secondNumber;

        if (fromNumber.compareTo(toNumber) < 0) {
            firstNumber = fromNumber;
            secondNumber = toNumber;
        } else {
            firstNumber = toNumber;
            secondNumber = fromNumber;
        }

        Account firstAccount = accountRepo
                .findByAccountNumberForUpdate(firstNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account not found: " + firstNumber));

        Account secondAccount = accountRepo
                .findByAccountNumberForUpdate(secondNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account not found: " + secondNumber));

        Account fromAccount = fromNumber.equals(firstNumber)
                ? firstAccount : secondAccount;

        Account toAccount = toNumber.equals(firstNumber)
                ? firstAccount : secondAccount;

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        LocalDateTime now = LocalDateTime.now();

        Transfer transfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .createdAt(now)
                .completedAt(now)
                .status(TransferStatus.COMPLETED)
                .build();

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);

        return transferRepo.save(transfer);
    }

    public List<Transfer> getTransfersByAccount(String accountNumber) {
        Account account =
                accountService.getAccountByAccountNumber(accountNumber);

        return transferRepo.findByFromAccountOrToAccount(
                account, account);
    }
}
