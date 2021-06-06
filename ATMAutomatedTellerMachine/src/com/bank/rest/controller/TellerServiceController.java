package com.bank.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.model.Account;
import com.bank.service.AccountAuthenticationService;
import com.bank.service.TellerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/teller")
public class TellerServiceController {

    @Autowired
    private TellerService tellerService;

    @Autowired
	private AccountAuthenticationService authenticationService;

    @RequestMapping(value = "/gettoken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String authorize(@RequestBody @Valid Account account) {
        return authenticationService.generateToken(account);
    }

    @RequestMapping("/withdraw/{token}/{accountnumber}/{amount}")
    public @ResponseBody String withdraw(@PathVariable(value = "token") String token, @PathVariable(value = "accountnumber") long accountNumber, @PathVariable(value = "amount") int amount) {
        try {
            if (authenticationService.verifyToken(accountNumber, token)) {
                if (tellerService.withdraw(accountNumber, amount)) {
                    return "Success";
                } else {
                    return "Error occured while updating your account";
                }
            } else {
                return "Invalid Token";
            }
        } catch (Exception e) {
            e.printStackTrace();
			throw new Exception("Error occured while updating your account" + e.getMessage(), e);
        }
    }

    @RequestMapping("/checkbalance/{token}/{accountnumber}")
    public @ResponseBody double checkBalance(@PathVariable(value = "token") String token, @PathVariable(value = "accountnumber") long accountNumber) {
        try {
            if (authenticationService.verifyToken(accountNumber, token)) {
                return tellerService.checkBalance(accountNumber);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @RequestMapping("/deposit/{token}/{accountnumber}/{amount}")
    public @ResponseBody String deposit(@PathVariable(value = "token") String token, @PathVariable(value = "accountnumber") long accountNumber, @PathVariable(value = "amount") int amount) {
        try {
            if (authenticationService.verifyToken(accountNumber, token)) {
                if (tellerService.deposit(accountNumber, amount)) {
                    return "Success";
                } else {
                    return "Error occured while updating your account";
                }
            } else {
                return "Invalid Token";
            }
        } catch (Exception e) {
            e.printStackTrace();
			throw new Exception("Error occured while updating your account" + e.getMessage(), e);
        }
    }

	@RequestMapping("/create/{accountname}/{pin}")
	public @ResponseBody String deposit(@PathVariable(value = "accountname") String accountName,
			@PathVariable(value = "pin") int pin) {
		try {
			return "Account created with ID: " + tellerService.createAccount(accountName, pin);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error occured while creating your account" + e.getMessage(), e);
		}
	}

}
