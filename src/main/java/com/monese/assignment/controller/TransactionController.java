package com.monese.assignment.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.monese.assignment.entity.Transaction;
import com.monese.assignment.exception.InsufficientFundsException;
import com.monese.assignment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createTransaction(@RequestBody Transaction transaction) {

        Integer transactionId = null;
        try {
            transactionId = transactionService.createTransaction(transaction);
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(e);
        }
        transaction.setId(transactionId);
        return ResponseEntity.status(CREATED).body(transaction);
    }
}
