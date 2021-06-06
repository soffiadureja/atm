package com.bank.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.repository.AccountRepository;

@Service
public class TellerServiceImpl implements TellerService {

    @Autowired
    private AccountRepository accountRepository;

    public double checkBalance(long accountNumber) {
        return accountRepository.getBalance(accountNumber);
    }

    @Transactional
    public boolean withdraw(long accountNumber, int amount) {
        double balance = accountRepository.getBalance(accountNumber);
        if (balance >= amount) {
            boolean result = accountRepository.withdraw(accountNumber, amount);
            if (result) {
                result = accountRepository.updateAccountTrnsaction(accountNumber, -amount);
                return result;
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("There is not enough balance in your account.");
        }
    }

    @Transactional
    public boolean deposit(long accountNumber, int amount) {
        if (amount > 0) {
            boolean result = accountRepository.deposit(accountNumber, amount);
            if (result) {
                result = accountRepository.updateAccountTrnsaction(accountNumber, amount);
                return result;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

	@Override
	public long createAccount(String accountName, int pin) throws SQLException {
		return accountRepository.createAccount(accountName, pin);
	}

}
