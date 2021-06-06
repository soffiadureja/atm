package com.bank.service;

import java.sql.SQLException;

public interface TellerService {

	double checkBalance(long accountNumber);

	boolean withdraw(long accountNumber, int amount);

	boolean deposit(long accountNumber, int amount);

	long createAccount(String accountName, int pin) throws SQLException;

}
