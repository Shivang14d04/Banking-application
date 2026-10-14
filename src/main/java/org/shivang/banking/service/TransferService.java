package org.shivang.banking.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepo transferRepo;
    private final AccountService accountService;
    private final AccountRepo accountRepo;
    @Transactional
    public Transfer transferMoney(TransferRequest transferRequest) {
        if(transferRequest.fromAccount().equals(transferRequest.toAccount())){
            throw new InvalidTransferException("can't transfer to same account");
        }
        Account fromAccount = accountService.getAccountByAccountNumber(transferRequest.fromAccount());
        Account toAccount = accountService.getAccountByAccountNumber(transferRequest.toAccount());


        if(transferRequest.amount()== null || transferRequest.amount().compareTo(BigDecimal.ZERO) <=0){
            throw new InvalidTransferException("Amount should be greater than zero");
        }

        if(fromAccount.getBalance().compareTo(transferRequest.amount()) < 0){
            throw new InsufficientBalanceException("Amount should be greater than current balance");
        }



        fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequest.amount()));
        toAccount.setBalance(toAccount.getBalance().add(transferRequest.amount()));
        Transfer transfer = Transfer.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(transferRequest.amount())
                .createdAt(LocalDateTime.now())
                .completedAt(LocalDateTime.now())
                .status(TransferStatus.COMPLETED)
                .build();

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);
        transferRepo.save(transfer);

        return transfer;
    }
}
