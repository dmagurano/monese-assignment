package com.monese.assignment.service;

import com.monese.assignment.entity.Account;
import com.monese.assignment.entity.Transaction;
import com.monese.assignment.exception.InsufficientFundsException;
import com.monese.assignment.repository.AccountRepository;
import com.monese.assignment.repository.TransactionRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Integer createTransaction(Transaction transaction) throws InsufficientFundsException {
        Account source = accountRepository.findById(transaction.getSourceAccount());
        BigDecimal transactionAmount = transaction.getAmount();

        if (source.getBalance().compareTo(transactionAmount) < 0) {
            throw new InsufficientFundsException(source, transactionAmount);
        }

        Account destination = accountRepository.findById(transaction.getDestinationAccount());

        source.subtractFromBalance(transactionAmount);
        destination.addToBalance(transactionAmount);

        accountRepository.update(source);
        accountRepository.update(destination);

        return transactionRepository.createTransaction(transaction);
    }
}
