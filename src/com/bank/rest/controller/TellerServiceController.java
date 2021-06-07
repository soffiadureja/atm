package com.bank.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public @ResponseBody ResponseEntity authorize(@RequestBody @Valid Account account) {
        return new ResponseEntity(authenticationService.generateToken(account), HttpStatus.OK);
    }

    @RequestMapping("/withdraw/{token}/{accountnumber}/{amount}")
	public @ResponseBody ResponseEntity withdraw(@PathVariable(value = "token") String token,
			@PathVariable(value = "accountnumber") long accountNumber, @PathVariable(value = "amount") int amount) {
        try {
            if (authenticationService.verifyToken(accountNumber, token)) {
                if (tellerService.withdraw(accountNumber, amount)) {
                	return new ResponseEntity("Success", HttpStatus.OK);
                } else {
                	return new ResponseEntity("Error occured while updating your account", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
            	return new ResponseEntity("Invalid Token", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Error occured while updating your account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/checkbalance/{token}/{accountnumber}")
	public @ResponseBody ResponseEntity checkBalance(@PathVariable(value = "token") String token,
			@PathVariable(value = "accountnumber") long accountNumber) {
        try {
            if (authenticationService.verifyToken(accountNumber, token)) {
				return new ResponseEntity("tellerService.checkBalance(accountNumber)", HttpStatus.OK);
            } else {
            	return new ResponseEntity("Invalid Token", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Error occured while retrieving balance: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/deposit/{token}/{accountnumber}/{amount}")
	public @ResponseBody ResponseEntity deposit(@PathVariable(value = "token") String token,
			@PathVariable(value = "accountnumber") long accountNumber, @PathVariable(value = "amount") int amount) {
        try {
            if (authenticationService.verifyToken(accountNumber, token)) {
                if (tellerService.deposit(accountNumber, amount)) {
                    return new ResponseEntity("Success", HttpStatus.OK);
                } else {
                    return new ResponseEntity("Error occured while updating your account", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Invalid Token", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Error occured while updating your account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping("/create/{accountname}/{pin}")
	public @ResponseBody ResponseEntity createAccount(@PathVariable(value = "accountname") String accountName,
			@PathVariable(value = "pin") int pin) {
		try {
			return new ResponseEntity("Account created with ID: " + tellerService.createAccount(accountName, pin), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Error occured while creating your account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
