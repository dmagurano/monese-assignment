package com.monese.assignment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.monese.assignment.entity.Account;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountE2ETest {

	@Autowired
	WebTestClient webTestClient;

	@Test
	void canGetAccountsById() {
		Account account = webTestClient
			.get()
			.uri("account/{accountId}", 1)
			.exchange()
			.expectStatus()
			.isOk()
			.returnResult(Account.class)
			.getResponseBody().blockFirst();

		assertThat(account.getBalance().compareTo(BigDecimal.valueOf(200)), equalTo(0));
	}
}

