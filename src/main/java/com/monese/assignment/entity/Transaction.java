package com.monese.assignment.entity;

import com.monese.assignment.dto.TransactionRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Integer id;
    private int sourceAccount;
    private int destinationAccount;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction(int sourceAccount, int destinationAccount, BigDecimal amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public static Transaction fromRequest(TransactionRequest transactionRequest) {
        return new Transaction(
            transactionRequest.getSourceAccount(),
            transactionRequest.getDestinationAccount(),
            transactionRequest.getAmount()
        );
    }
}
