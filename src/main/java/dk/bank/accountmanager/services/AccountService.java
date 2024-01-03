package dk.bank.accountmanager.services;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.exceptions.InvalidAccountNameException;
import dk.bank.accountmanager.exceptions.InvalidAmountException;
import dk.bank.accountmanager.interfaces.IAccountRepository;
import dk.bank.accountmanager.interfaces.IAccountService;

@Service
public class AccountService implements IAccountService {
	
	@Autowired
	IAccountRepository accountRepository;

	@Override
	public Account createAccount(Account account) throws InvalidAccountNameException {
		
		if(!isAccountNameValid(account.getName())) {
			throw new InvalidAccountNameException("Illegal symbol used in account name");
		}
		

		Account createdAccount = saveAndReturnAccount(account);
		
		return createdAccount;
	}

	@Override
	public Account getAccount(long accountId) throws Exception {
		Account account = findAccount(accountId);
		
		return account;
	}

	@Override
	public Account depositMoney(long accountId, double amount) throws InvalidAmountException {
		if (amount < 0) {
			throw new InvalidAmountException("Recieved invalid deposit amount: " + amount);
		}
		
		Account account = findAccount(accountId);
		
		double newBalance = amount + account.getBalance();
		account.setBalance(newBalance);
		
		Account updatedAccount = saveAndReturnAccount(account);
		
		return updatedAccount;
	}

	@Override
	public Account withdrawMoney(long accountId, double amount) throws InvalidAmountException {
		Account account = findAccount(accountId);
		
		if (amount < 0 || amount > account.getBalance()) {
			throw new InvalidAmountException("Recieved invalid withdrawal amount: " + amount);
		}
		
		double newBalance = account.getBalance() - amount;
		account.setBalance(newBalance);
		
		Account updatedAccount = saveAndReturnAccount(account);
		
		return updatedAccount;
	}

	@Override
	public double getAvailableBalance(long accountId) {
		Account account = accountRepository.getAccount(accountId);
		return account.getBalance();
	}
	
	private boolean isAccountNameValid(String name) {
		boolean isValid = name.matches("^[a-zA-Z0-9]*$") ?  true:  false;
		return isValid;
	}
	
	private Account saveAndReturnAccount(Account account) throws IllegalArgumentException{
		long accountId = accountRepository.saveAccount(account);
		Account savedAccount = findAccount(accountId);
		return savedAccount;
	}
	
	private Account findAccount(long accountId) throws IllegalArgumentException {
		Account account;
		try {
			account = Objects.requireNonNull(accountRepository.getAccount(accountId), "repository didn't find account");
		}
		catch (NullPointerException e) {
			throw new IllegalArgumentException("Account not found");
		}
		return account;
	}

}
