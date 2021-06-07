package com.bank.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bank.model.Account;
import com.bank.util.DBUtil;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private JdbcTemplate template;

    public AccountRepositoryImpl() {
        this.template = DBUtil.getJdbcTemplate();

    }

    public double getBalance(long accountNumber) {
        return template.queryForObject("select BALANCE from BANK.ACCOUNT where ACCOUNT_NUMBER=?", new Object[] { accountNumber }, new RowMapper<Double>() {

            public Double mapRow(ResultSet rowSet, int arg1) throws SQLException {

                return rowSet.getDouble("BALANCE");
            }
        });

    }

    @Override
    public boolean withdraw(long accountNumber, int amount) {
        return template.update("update BANK.ACCOUNT set BALANCE=BALANCE-? where ACCOUNT_NUMBER=?", new Object[] { amount, accountNumber }) == 1;
    }

    @Override
    public boolean deposit(long accountNumber, int amount) {
        return template.update("update BANK.ACCOUNT set BALANCE=BALANCE+? where ACCOUNT_NUMBER=?", new Object[] { amount, accountNumber }) == 1;

    }

    @Override
    public boolean updateAccountTrnsaction(long accountNumber, int amount) {
        return template.update("insert into BANK.TRANSACTION_DETAILS(ACCOUNT_NUMBER,TRANSACTION_TIME,TRANSACTION_AMOUNT) values(?,?,?)", new Object[] { accountNumber, new Date(), amount }) == 1;
    }

    @Override
    public boolean verifyAccount(Account account) {
        return template.queryForObject("select count(*) as ACCOUNT_COUNT from BANK.ACCOUNT where ACCOUNT_NUMBER=? AND ACCOUNT_NAME=? AND PIN=?", new Object[] { account.getAccountNumber(), account.getAccountName(),
                account.getPin() }, new RowMapper<Integer>() {

            public Integer mapRow(ResultSet rowSet, int arg1) throws SQLException {

                return rowSet.getInt("ACCOUNT_COUNT");
            }
        }) == 1;

    }

	@Override
	public long createAccount(String accountName, int pin) throws SQLException {
		long accountNumber = (long) template.queryForObject("select max(ACCOUNT_NUMBER) from BANK.ACCOUNT",
				new RowMapper<Long>() {

					public Long mapRow(ResultSet rowSet, int arg1) throws SQLException {

						return rowSet.getLong("ACCOUNT_NUMBER");
					}
				});
		return template.update("insert into BANK.ACCOUNT values (?,?,?,?)",
				new Object[] { accountNumber, accountName, 0.00, pin });
	}

}
