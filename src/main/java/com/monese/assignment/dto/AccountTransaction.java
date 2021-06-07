package com.monese.assignment.dto;

import com.monese.assignment.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AccountTransaction {

    private final int transactionId;
    private final int foreignAccount;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

    public static AccountTransaction fromTransaction(int accountId, Transaction transaction) {
        boolean isDebit = accountId == transaction.getSourceAccount();

        return new AccountTransaction(
            transaction.getId(),
            isDebit ? transaction.getDestinationAccount() : transaction.getSourceAccount(),
            isDebit ? transaction.getAmount().negate() : transaction.getAmount(),
            transaction.getTimestamp()
        );
    }
}
