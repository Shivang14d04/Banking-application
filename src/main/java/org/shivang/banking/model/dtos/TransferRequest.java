package org.shivang.banking.model.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequest(
    @NotBlank(message= "Source account is required")
    String fromAccount,
    @NotBlank(message= "Destination account is required")
    String toAccount,
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Amount must be greater than zero")
    BigDecimal amount)
{}
