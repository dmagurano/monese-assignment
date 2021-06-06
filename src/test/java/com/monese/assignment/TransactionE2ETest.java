package com.monese.assignment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import com.monese.assignment.entity.Account;
import com.monese.assignment.entity.Transaction;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionE2ETest {

    private static final int SOURCE_ACCOUNT = 1;
    private static final int DESTINATION_ACCOUNT = 2;
    private static final BigDecimal TRANSACTION_AMOUNT = BigDecimal.valueOf(20);

    @Autowired
    WebTestClient webTestClient;

    @Test
    void canTransferFundsBetweenTwoAccounts() {
        BigDecimal sourceOriginalBalance = getAccountBalance(SOURCE_ACCOUNT);
        BigDecimal destinationOriginalBalance = getAccountBalance(DESTINATION_ACCOUNT);

        Transaction transaction = new Transaction(SOURCE_ACCOUNT, DESTINATION_ACCOUNT, TRANSACTION_AMOUNT);

        executeTransaction(transaction);

        BigDecimal sourceFinalBalance = getAccountBalance(SOURCE_ACCOUNT);
        BigDecimal destinationFinalBalance = getAccountBalance(DESTINATION_ACCOUNT);

        assertThat(sourceFinalBalance.compareTo(sourceOriginalBalance.subtract(TRANSACTION_AMOUNT)), equalTo(0));
        assertThat(destinationFinalBalance.compareTo(destinationOriginalBalance.add(TRANSACTION_AMOUNT)), equalTo(0));
    }

    private void executeTransaction(Transaction transaction) {
        webTestClient
            .post()
            .uri("/transaction")
            .bodyValue(transaction)
            .exchange()
            .expectStatus()
            .isCreated();
    }

    private BigDecimal getAccountBalance(int accountId) {
        Account account = webTestClient
            .get()
            .uri("account/{accountId}", accountId)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Account.class)
            .getResponseBody().blockFirst();

        return account.getBalance();
    }
}
