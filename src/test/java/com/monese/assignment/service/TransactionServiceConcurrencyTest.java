package com.monese.assignment.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

import com.monese.assignment.entity.Account;
import com.monese.assignment.entity.Transaction;
import com.monese.assignment.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionServiceConcurrencyTest {

    private static final int N_THREADS = 100;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void canSafelyTransferFundsWithMultipleConcurrentRequests() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(N_THREADS);
        ExecutorService threadPool = Executors.newFixedThreadPool(N_THREADS);

        BigDecimal amount = BigDecimal.ONE;
        BigDecimal totalAmount = amount.multiply(BigDecimal.valueOf(N_THREADS));
        Transaction transaction = new Transaction(1, 2, amount);

        for (int i=0; i < N_THREADS; i++) {
            threadPool.submit(() -> {
                try {
                    transactionService.createTransaction(transaction);
                }
                finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);

        Account sourceAccount = accountRepository.findById(1);
        Account destinationAccount = accountRepository.findById(2);

        assertThat(sourceAccount.getBalance(), comparesEqualTo(BigDecimal.valueOf(200).subtract(totalAmount)));
        assertThat(destinationAccount.getBalance(), comparesEqualTo(BigDecimal.valueOf(300).add(totalAmount)));
    }
}