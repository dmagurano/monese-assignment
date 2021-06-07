package com.monese.assignment.entity;

import java.util.List;
import lombok.Data;

@Data
public class AccountSummary {
    private final Account account;
    private final List<Transaction> transactions;
}
