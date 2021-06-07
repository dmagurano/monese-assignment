package com.monese.assignment.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.monese.assignment.dto.TransactionRequest;
import com.monese.assignment.entity.Transaction;
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
    public ResponseEntity createTransaction(@RequestBody TransactionRequest transactionRequest) {

        Transaction transaction = transactionService.createTransaction(Transaction.fromRequest(transactionRequest));
        return ResponseEntity.status(CREATED).body(transaction);
    }
}
