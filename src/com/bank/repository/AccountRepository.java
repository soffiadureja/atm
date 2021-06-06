package com.bank.repository;

import java.sql.SQLException;

import com.bank.model.Account;

public interface AccountRepository {

	double getBalance(long accountNumber);

	boolean withdraw(long accountNumber, int amount);

	boolean deposit(long accountNumber, int amount);

	boolean updateAccountTrnsaction(long accountNumber, int amount);

    boolean verifyAccount(Account account);

	long createAccount(String accountNumber, int pin) throws SQLException;
}
