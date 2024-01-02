package dk.bank.accountmanager.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.interfaces.IAccountRepository;
import dk.bank.accountmanager.interfaces.IAccountService;

@Service
public class AccountService implements IAccountService {
	
	@Autowired
	IAccountRepository accountRepository;

	@Override
	public Account createAccount(String name) throws Exception {
		
		return null;
	}

	@Override
	public Account getAccount(long accountId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account depositMoney(long accountId, double amount) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account withdrawMoney(long accountId, double amount) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getAvailableBalance(long accountId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
