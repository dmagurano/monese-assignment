package com.monese.assignment.dto;

import static java.util.stream.Collectors.toList;

import com.monese.assignment.entity.AccountSummary;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private int id;
    private BigDecimal balance;
    private List<AccountTransaction> transactions;

    public static AccountResponse fromSummary(AccountSummary accountSummary) {

        int accountId = accountSummary.getAccount().getId();

        return new AccountResponse(
            accountId,
            accountSummary.getAccount().getBalance(),
            accountSummary.getTransactions()
                .stream()
                .map((transaction) -> AccountTransaction.fromTransaction(
                    accountId,
                    transaction))
                .collect(toList())
        );
    }
}
