package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    @JsonProperty("accountName")
    private String accountName;
    
    @JsonProperty("accountNumber")
    private long accountNumber;
    
    @JsonProperty("pin")
    private int pin;
    
    @JsonProperty("balance")
    private double balance;

    public String getAccountName() {
        return accountName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
}
