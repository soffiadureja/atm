package com.bank.service;

import com.bank.model.Account;

public interface AccountAuthenticationService {

    String generateToken(Account account);

    boolean verifyToken(long accountNumber, String token);

}
