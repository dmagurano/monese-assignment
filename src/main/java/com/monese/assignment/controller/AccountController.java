package com.monese.assignment.controller;

import com.monese.assignment.entity.Account;
import com.monese.assignment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccount(@PathVariable("accountId") int accountId) {

        Account account = accountRepository.findById(accountId);

        return ResponseEntity.ok(account);
    }
}
