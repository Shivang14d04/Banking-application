package org.shivang.banking.model.dtos;

import org.shivang.banking.model.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(
        Long id,
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        TransferStatus status) {
}
