package org.shivang.banking.model.dtos;

import java.math.BigDecimal;

public record TransferRequest(
    String fromAccount,
    String toAccount,
    BigDecimal amount)
{}
