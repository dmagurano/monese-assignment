package com.monese.assignment.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private int id;
    private BigDecimal balance;

    public void addToBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void subtractFromBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
