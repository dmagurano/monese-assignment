package com.monese.assignment.repository;

import com.monese.assignment.entity.Transaction;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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

    public Transaction createTransaction(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime timestamp = LocalDateTime.now();

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("source", transaction.getSourceAccount())
            .addValue("destination", transaction.getDestinationAccount())
            .addValue("amount", transaction.getAmount())
            .addValue("timestamp", Timestamp.valueOf(timestamp));

        jdbcTemplate.update("INSERT INTO [Transaction] (Source, Destination, Amount, Timestamp) " +
                                "VALUES (:source, :destination, :amount, :timestamp)                ",
                            params, keyHolder);

        transaction.setId(keyHolder.getKey().intValue());
        transaction.setTimestamp(timestamp);

        return transaction;
    }

    public List<Transaction> getAccountTransactions(int accountId) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("accountId", accountId);

        return jdbcTemplate.query("SELECT Id, Source, Destination, Amount, Timestamp " +
                                      "FROM [Transaction] t " +
                                      "WHERE Source = :accountId " +
                                      "OR Destination = :accountId",
                                  params,
                                  (rs, i) -> new Transaction(
                                      rs.getInt("Id"),
                                      rs.getInt("Source"),
                                      rs.getInt("Destination"),
                                      rs.getBigDecimal("Amount"),
                                      rs.getTimestamp("Timestamp").toLocalDateTime()
                                  )
        );
    }
}
