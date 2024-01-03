package dk.bank.accountmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.entity.Transaction;
import dk.bank.accountmanager.interfaces.IAccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
	
	@Autowired
	private IAccountService accountService;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> createAccount(@RequestBody Account account) throws Exception{	
		Account newAccount = accountService.createAccount(account);
		return new ResponseEntity<>(newAccount, HttpStatus.CREATED);	
	}
	
	@PutMapping(value = "/deposit/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> depositMoney(@PathVariable Long accountId, @RequestBody double amount ) throws Exception{	
		Account updatedAccount = accountService.depositMoney(accountId, amount);
		return new ResponseEntity<>(updatedAccount, HttpStatus.OK);	
	}
	
	@PutMapping(value = "/withdraw/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> withdrawMoney(@PathVariable Long accountId, @RequestBody double amount ) throws Exception{	
		Account updatedAccount = accountService.withdrawMoney(accountId, amount);
		return new ResponseEntity<>(updatedAccount, HttpStatus.OK);	
	}
	
	@GetMapping(value = "/balance/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getAvailableBalance(@PathVariable Long accountId) throws Exception{	
		double availableBalance = accountService.getAvailableBalance(accountId);
		return new ResponseEntity<>(availableBalance, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{accountId}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long accountId, 
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) throws Exception{	
		List<Transaction> transactions = accountService.getTransactions(accountId, size);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

}
