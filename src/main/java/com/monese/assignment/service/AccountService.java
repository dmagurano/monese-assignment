package com.monese.assignment.service;

import com.monese.assignment.entity.AccountSummary;
import com.monese.assignment.repository.AccountRepository;
import com.monese.assignment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public AccountSummary getSummaryById(int accountId) {
        return new AccountSummary(accountRepository.findById(accountId), transactionRepository.getAccountTransactions(accountId));
    }
}
