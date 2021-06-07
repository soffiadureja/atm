package com.bank.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;

@Service
public class AccountAuthenticationServiceImpl implements AccountAuthenticationService {

    @Autowired
    private AccountRepository accountRepository;

    @Value("${ignoreAuth}")
    private boolean ignoreAuth;

    ConcurrentHashMap<Long, String> authDetails = new ConcurrentHashMap<Long, String>();

    public String generateToken(Account account) {
        boolean result = accountRepository.verifyAccount(account);
        if (result == true) {
            String token = UUID.randomUUID().toString();
            authDetails.put(new Long(account.getAccountNumber()), token);
            return token;
        } else {
            return "Invalid Account or PIN";
        }
    }

    @Override
    public boolean verifyToken(long accountNumber, String token) {
        if (ignoreAuth) {
            return true;
        }
        String existingToken = authDetails.get(new Long(accountNumber));

        if (existingToken != null && existingToken.equals(token)) {
            authDetails.remove(new Long(accountNumber));
            return true;
        } else {
            return false;
        }
    }

}
