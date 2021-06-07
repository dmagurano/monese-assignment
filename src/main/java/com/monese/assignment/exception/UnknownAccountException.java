package com.monese.assignment.exception;

public class UnknownAccountException extends RuntimeException {

    public UnknownAccountException(int accountId) {
        super(String.format("Unknown account: %d", accountId));
    }
}
