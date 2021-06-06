package com.monese.assignment.repository;

import com.monese.assignment.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Account findById(int accountId) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("accountId", accountId);

        return jdbcTemplate.queryForObject("SELECT Id, Balance   " +
                                               "FROM Account         " +
                                               "WHERE Id = :accountId",
                                           params,
                                           (rs, i) -> new Account(
                                               rs.getInt("Id"),
                                               rs.getBigDecimal("Balance")
                                           ));
    }

    public void update(Account account) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("accountId", account.getId())
            .addValue("balance", account.getBalance());

        jdbcTemplate.update("UPDATE Account         " +
                                "SET Balance = :balance " +
                                "WHERE Id = :accountId  ",
                            params);
    }
}
