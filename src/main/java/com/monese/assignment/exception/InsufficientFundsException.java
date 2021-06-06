package com.monese.assignment.exception;

import com.monese.assignment.entity.Account;
import java.math.BigDecimal;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(Account account, BigDecimal amount) {
        super(String.format("Insufficient funds for account %d. Available: %.2f, Required: %.2f",
                            account.getId(),
                            account.getBalance(),
                            amount));
    }
}
