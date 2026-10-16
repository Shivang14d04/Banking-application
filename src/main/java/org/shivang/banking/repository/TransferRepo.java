package org.shivang.banking.repository;

import org.shivang.banking.model.Account;
import org.shivang.banking.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepo extends JpaRepository<Transfer, Long> {

    List<Transfer> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
}
