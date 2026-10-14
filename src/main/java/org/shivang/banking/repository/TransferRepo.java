package org.shivang.banking.repository;

import org.shivang.banking.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepo extends JpaRepository<Transfer, Long> {
}
