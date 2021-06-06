package com.monese.assignment.repository;

import com.monese.assignment.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Integer createTransaction(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("source", transaction.getSourceAccount())
            .addValue("destination", transaction.getDestinationAccount())
            .addValue("amount", transaction.getAmount());

        jdbcTemplate.update("INSERT INTO Transaction (Source, Destination, Amount) " +
                                "VALUES (:source, :destination, :amount)              ",
                            params, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
