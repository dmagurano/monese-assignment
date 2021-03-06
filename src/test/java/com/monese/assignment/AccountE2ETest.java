package com.monese.assignment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

import com.monese.assignment.dto.AccountResponse;
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
		AccountResponse account = webTestClient
			.get()
			.uri("account/{accountId}", 1)
			.exchange()
			.expectStatus()
			.isOk()
			.returnResult(AccountResponse.class)
			.getResponseBody().blockFirst();

		assertThat(account.getBalance(), comparesEqualTo(BigDecimal.valueOf(200)));
	}

	@Test
	void returnBadRequestWhenAccountDoesntExist() {
		webTestClient
			.get()
			.uri("account/{accountId}", 9)
			.exchange()
			.expectStatus()
			.isBadRequest();
	}
}

