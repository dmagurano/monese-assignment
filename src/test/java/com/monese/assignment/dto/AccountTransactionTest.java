package com.monese.assignment.dto;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monese.assignment.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AccountTransactionTest {

    private static final int SOURCE_ACCOUNT = 1;
    private static final int DESTINATION_ACCOUNT = 2;

    @Test
    void createDebitAccountTransaction() {
        Transaction transaction = new Transaction(1, SOURCE_ACCOUNT, DESTINATION_ACCOUNT, BigDecimal.TEN, LocalDateTime.now());

        AccountTransaction accountTransaction = AccountTransaction.fromTransaction(SOURCE_ACCOUNT, transaction);

        assertThat(accountTransaction.getTransactionId(), equalTo(transaction.getId()));
        assertThat(accountTransaction.getForeignAccount(), equalTo(DESTINATION_ACCOUNT));
        assertThat(accountTransaction.getAmount(), equalTo(transaction.getAmount().negate()));
        assertThat(accountTransaction.getTimestamp(), equalTo(transaction.getTimestamp()));
    }

    @Test
    void createCreditAccountTransaction() {
        Transaction transaction = new Transaction(1, DESTINATION_ACCOUNT, SOURCE_ACCOUNT, BigDecimal.TEN, LocalDateTime.now());

        AccountTransaction accountTransaction = AccountTransaction.fromTransaction(SOURCE_ACCOUNT, transaction);

        assertThat(accountTransaction.getTransactionId(), equalTo(transaction.getId()));
        assertThat(accountTransaction.getForeignAccount(), equalTo(DESTINATION_ACCOUNT));
        assertThat(accountTransaction.getAmount(), equalTo(transaction.getAmount()));
        assertThat(accountTransaction.getTimestamp(), equalTo(transaction.getTimestamp()));
    }
}