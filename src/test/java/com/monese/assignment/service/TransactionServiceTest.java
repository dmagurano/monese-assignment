package com.monese.assignment.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.monese.assignment.entity.Account;
import com.monese.assignment.entity.Transaction;
import com.monese.assignment.exception.InsufficientFundsException;
import com.monese.assignment.repository.AccountRepository;
import com.monese.assignment.repository.TransactionRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class TransactionServiceTest {

    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final AccountRepository accountRepository = mock(AccountRepository.class);

    private TransactionService transactionService = new TransactionService(transactionRepository, accountRepository);

    @Test
    void whenTransactionToSameAccountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(1, 1, BigDecimal.TEN);
            transactionService.createTransaction(transaction);
        });
    }

    @Test
    void whenFundsAreInsufficientThenThrowsInsufficientFundsException() {

        when(accountRepository.findById(1)).thenReturn(new Account(1, BigDecimal.TEN));

        assertThrows(InsufficientFundsException.class, () -> {
            Transaction transaction = new Transaction(1, 2, BigDecimal.valueOf(20));
            transactionService.createTransaction(transaction);
        });
    }
}