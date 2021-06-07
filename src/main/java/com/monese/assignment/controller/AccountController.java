package com.monese.assignment.controller;

import com.monese.assignment.dto.AccountResponse;
import com.monese.assignment.entity.AccountSummary;
import com.monese.assignment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccount(@PathVariable("accountId") int accountId) {

        AccountSummary account = accountService.getSummaryById(accountId);

        return ResponseEntity.ok(AccountResponse.fromSummary(account));
    }
}
