package com.monese.assignment;

import static org.exparity.hamcrest.date.LocalDateTimeMatchers.within;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;

import com.monese.assignment.dto.AccountResponse;
import com.monese.assignment.dto.AccountTransaction;
import com.monese.assignment.dto.TransactionRequest;
import com.monese.assignment.entity.Transaction;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionE2ETest {

    private static final int SOURCE_ACCOUNT = 1;
    private static final int DESTINATION_ACCOUNT = 2;
    private static final BigDecimal TRANSACTION_AMOUNT = BigDecimal.valueOf(20);

    @Autowired
    WebTestClient webTestClient;

    @Test
    void canTransferFundsBetweenTwoAccounts() {
        AccountResponse originalSourceAccount = getAccount(SOURCE_ACCOUNT);
        AccountResponse originalDestinationAccount = getAccount(DESTINATION_ACCOUNT);

        TransactionRequest request = new TransactionRequest(SOURCE_ACCOUNT, DESTINATION_ACCOUNT, TRANSACTION_AMOUNT);

        Transaction transaction = executeTransaction(request);

        AccountResponse finalSourceAccount = getAccount(SOURCE_ACCOUNT);
        AccountResponse finalDestinationAccount = getAccount(DESTINATION_ACCOUNT);

        assertThat(finalSourceAccount.getBalance(), comparesEqualTo(originalSourceAccount.getBalance().subtract(TRANSACTION_AMOUNT)));
        assertThat(finalDestinationAccount.getBalance(), comparesEqualTo(originalDestinationAccount.getBalance().add(TRANSACTION_AMOUNT)));

        assertTransactionPresentInSourceAccount(transaction, finalSourceAccount);
        assertTransactionPresentInDestinationAccount(transaction, finalDestinationAccount);
    }

    private void assertTransactionPresentInDestinationAccount(Transaction transaction, AccountResponse finalDestinationAccount) {
        AccountTransaction destinationAccountTransaction = finalDestinationAccount.getTransactions().get(0);

        assertThat(destinationAccountTransaction.getTransactionId(), equalTo(transaction.getId()));
        assertThat(destinationAccountTransaction.getForeignAccount(), equalTo(transaction.getSourceAccount()));
        assertThat(destinationAccountTransaction.getAmount(), comparesEqualTo(transaction.getAmount()));
        assertThat(destinationAccountTransaction.getTimestamp(), within(1, ChronoUnit.SECONDS, transaction.getTimestamp()));
    }

    private void assertTransactionPresentInSourceAccount(Transaction transaction, AccountResponse finalSourceAccount) {
        AccountTransaction sourceAccountTransaction = finalSourceAccount.getTransactions().get(0);

        assertThat(sourceAccountTransaction.getTransactionId(), equalTo(transaction.getId()));
        assertThat(sourceAccountTransaction.getForeignAccount(), equalTo(transaction.getDestinationAccount()));
        assertThat(sourceAccountTransaction.getAmount(), comparesEqualTo(transaction.getAmount().negate()));
        assertThat(sourceAccountTransaction.getTimestamp(), within(1, ChronoUnit.SECONDS, transaction.getTimestamp()));
    }

    @Test
    void returnBadRequestIfFundsAreInsufficient() {
        Transaction transactionRequest = new Transaction(SOURCE_ACCOUNT, DESTINATION_ACCOUNT, BigDecimal.valueOf(999999));

        webTestClient
            .post()
            .uri("/transaction")
            .bodyValue(transactionRequest)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void returnBadRequestIfTransactionToSameAccount() {
        Transaction transactionRequest = new Transaction(SOURCE_ACCOUNT, SOURCE_ACCOUNT, TRANSACTION_AMOUNT);

        webTestClient
            .post()
            .uri("/transaction")
            .bodyValue(transactionRequest)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    private Transaction executeTransaction(TransactionRequest transaction) {
        return webTestClient
            .post()
            .uri("/transaction")
            .bodyValue(transaction)
            .exchange()
            .expectStatus()
            .isCreated()
            .returnResult(Transaction.class)
            .getResponseBody().blockFirst();
    }

    private AccountResponse getAccount(int accountId) {
        return webTestClient
            .get()
            .uri("account/{accountId}", accountId)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(AccountResponse.class)
            .getResponseBody().blockFirst();
    }
}
