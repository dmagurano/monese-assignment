package com.monese.assignment.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Transaction {

    private Integer id;
    private final int sourceAccount;
    private final int destinationAccount;
    private final BigDecimal amount;
}
