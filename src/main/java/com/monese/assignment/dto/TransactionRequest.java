package com.monese.assignment.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private int sourceAccount;
    private int destinationAccount;
    private BigDecimal amount;
}
