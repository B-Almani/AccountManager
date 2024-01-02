package dk.bank.accountmanager.interfaces;

import java.util.List;

import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.entity.Transaction;

public interface IAccountService {
	
	Account createAccount(String name) throws Exception;
	Account getAccount(long accountId) throws Exception;
	Account depositMoney(long accountId, double amount) throws Exception;
	Account withdrawMoney(long accountId, double amount) throws Exception;
	double getAvailableBalance (long accountId);
	
}
